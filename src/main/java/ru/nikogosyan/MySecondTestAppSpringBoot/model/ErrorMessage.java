package ru.nikogosyan.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorMessage {

    EMPTY(""),
    VALIDATION("Ошибка валидации"),
    UNSUPPORTED("Произошла непредвиденная ошибка"),
    UNKNOWN("Неизвестная ошибка");

    private final String description;

    ErrorMessage(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
