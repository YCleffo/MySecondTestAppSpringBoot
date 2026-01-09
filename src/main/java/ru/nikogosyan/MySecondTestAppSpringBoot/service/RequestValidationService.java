package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.nikogosyan.MySecondTestAppSpringBoot.exception.ValidationFailedException;

@Service
public class RequestValidationService implements ValidationService {

    private static final Logger log = LoggerFactory.getLogger(RequestValidationService.class);

    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            String fieldName = bindingResult.getFieldError().getField();

            log.error("ОШИБКА ВАЛИДАЦИИ: поле='{}', ошибка='{}'", fieldName, errorMessage);
            throw new ValidationFailedException(errorMessage);
        }
        log.info("Валидация пройдена успешно");
    }
}