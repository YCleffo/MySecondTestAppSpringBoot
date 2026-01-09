package ru.nikogosyan.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nikogosyan.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.nikogosyan.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.*;
import ru.nikogosyan.MySecondTestAppSpringBoot.service.ModifyRequestService;
import ru.nikogosyan.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.nikogosyan.MySecondTestAppSpringBoot.service.ValidationService;
import ru.nikogosyan.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;

    private final ModifyResponseService modifyResponseService;

    private final ModifyRequestService modifyRequestService;


    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        ModifyRequestService modifyRequestService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {
        log.info("request:{}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCode.EMPTY)
                .errorMessage(ErrorMessage.EMPTY)
                .build();

        log.info("Создан начальный response: uid={}, operationUid={}",
                response.getUid(), response.getOperationUid());

        try {
            validationService.isValid(bindingResult);
            log.info("Валидация пройдена успешно для uid={}", request.getUid());
            if ("123".equals(request.getUid())) {
                log.warn("Обнаружен неподдерживаемый uid={}", request.getUid());
                throw new UnsupportedCodeException("UID 123 не поддерживается системой");
            }
            modifyResponseService.modify(response);

            modifyRequestService.modify(request);

            log.info("Запрос успешно обработан и отправлен в Сервис 2. Возвращаем ответ клиенту: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ValidationFailedException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCode.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessage.VALIDATION);
            log.error("Ошибка валидации: {}", e.getMessage());
            log.info("Response изменен1: code={}, errorCode={}, errorMessage={}",
                    response.getCode(), response.getErrorCode(), response.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (UnsupportedCodeException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCode.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessage.UNSUPPORTED);
            log.error("Неподдерживаемый код: {}", e.getMessage());
            log.info("Response изменен2: code={}, errorCode={}, errorMessage={}",
                    response.getCode(), response.getErrorCode(), response.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCode.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessage.UNKNOWN);
            log.error("Непредвиденная ошибка: {}", e.getMessage(), e);
            log.info("Response изменен3: code={}, errorCode={}, errorMessage={}",
                    response.getCode(), response.getErrorCode(), response.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}