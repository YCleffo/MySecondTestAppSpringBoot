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

import java.time.Instant;
import java.util.Date;

@Slf4j
@RestController
public class MyController {
    private static final String UNSUPPORTED_UID = "123";

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService")
                        ModifyResponseService modifyResponseService,
                        @Qualifier("modifyRequestSystemSourceService")
                        ModifyRequestService modifyRequestService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {
        request.setSystemTime(Instant.now().toString());
        log.info("Incoming request: {}", request);

        Response response = createInitialResponse(request);

        try {
            processRequest(request, bindingResult, response);
            log.info("Request processed successfully. Response: {}", response);
            return ResponseEntity.ok(response);

        } catch (ValidationFailedException e) {
            return handleValidationException(response, e);
        } catch (UnsupportedCodeException e) {
            return handleUnsupportedCodeException(response, e);
        } catch (Exception e) {
            return handleGenericException(response, e);
        }
    }

    private Response createInitialResponse(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCode.EMPTY)
                .errorMessage(ErrorMessage.EMPTY)
                .build();
    }

    private void processRequest(Request request, BindingResult bindingResult, Response response)
            throws ValidationFailedException, UnsupportedCodeException {
        validationService.isValid(bindingResult);
        log.info("Validation passed for uid={}", request.getUid());

        checkUidSupport(request.getUid());
        modifyResponseService.modify(response);
        modifyRequestService.modify(request);
    }

    private void checkUidSupport(String uid) throws UnsupportedCodeException {
        if (UNSUPPORTED_UID.equals(uid)) {
            log.warn("Unsupported uid detected: {}", uid);
            throw new UnsupportedCodeException("UID " + uid + " is not supported by the system");
        }
    }

    private ResponseEntity<Response> handleValidationException(Response response, ValidationFailedException e) {
        updateErrorResponse(response, Codes.FAILED, ErrorCode.VALIDATION_EXCEPTION, ErrorMessage.VALIDATION);
        log.error("Validation error: {}", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    private ResponseEntity<Response> handleUnsupportedCodeException(Response response, UnsupportedCodeException e) {
        updateErrorResponse(response, Codes.FAILED, ErrorCode.UNSUPPORTED_EXCEPTION, ErrorMessage.UNSUPPORTED);
        log.error("Unsupported code error: {}", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    private ResponseEntity<Response> handleGenericException(Response response, Exception e) {
        updateErrorResponse(response, Codes.FAILED, ErrorCode.UNKNOWN_EXCEPTION, ErrorMessage.UNKNOWN);
        log.error("Unexpected error: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private void updateErrorResponse(Response response, Codes code, ErrorCode errorCode, ErrorMessage errorMessage) {
        response.setCode(code);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        log.info("Response updated: code={}, errorCode={}, errorMessage={}",
                response.getCode(), response.getErrorCode(), response.getErrorMessage());
    }
}