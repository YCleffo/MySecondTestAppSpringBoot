package ru.nikogosyan.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {

    EMPTY(""),
    VALIDATION_EXCEPTION("ValidationException"),
    UNKNOWN_EXCEPTION("UnknownException"),
    UNSUPPORTED_EXCEPTION("UnsupportedException");

    private final String name;

    ErrorCode(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
