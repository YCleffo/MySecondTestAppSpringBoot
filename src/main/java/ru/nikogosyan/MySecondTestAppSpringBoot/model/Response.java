package ru.nikogosyan.MySecondTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    /** Уникальный идентификатор сообщения */
    private String uid;

    /** Уникальный идентификатор операции */
    private String operationUid;

    /** Время отправки ответа */
    private String systemTime;

    /** Код выполнения операции */
    private Codes code;

    /** Размер годового бонуса */
    private Double annualBonus;

    /** Код ошибки */
    private ErrorCode errorCode;

    /** Текст ошибки */
    private ErrorMessage errorMessage;
}