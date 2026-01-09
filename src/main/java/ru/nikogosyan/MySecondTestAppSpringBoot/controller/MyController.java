package ru.nikogosyan.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nikogosyan.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.nikogosyan.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.*;
import ru.nikogosyan.MySecondTestAppSpringBoot.service.ValidationService;
import ru.nikogosyan.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCode.EMPTY)
                .errorMessage(ErrorMessage.EMPTY)
                .build();

        try {
            validationService.isValid(bindingResult);

            if ("123".equals(request.getUid())) {
                throw new UnsupportedCodeException("UID 123 не поддерживается системой");
            }

        } catch (ValidationFailedException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCode.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessage.VALIDATION);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (UnsupportedCodeException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCode.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessage.UNSUPPORTED);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCode.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessage.UNKNOWN);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}