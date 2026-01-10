package ru.nikogosyan.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    /** Уникальный идентификатор сообщения */
    @NotBlank(message = "UID не может быть пустым")
    @Size(max = 32, message = "UID не должен превышать 32 символа")
    private String uid;

    /** Уникальный идентификатор операции */
    @NotBlank(message = "Operation UID не может быть пустым")
    @Size(max = 32, message = "Operation UID не должен превышать 32 символа")
    private String operationUid;

    /** Имя системы, отправившей запрос */
    @NotNull(message = "Имя системы не может быть пустым")
    private Systems systemName;

    /** Время создания сообщения в системе */
    @NotBlank(message = "Системное время не может быть пустым")
    private String systemTime;

    /** Наименование ресурса */
    @NotBlank(message = "Источник не может быть пустым")
    private String source;

    /** Должность сотрудника */
    private Position position;

    /** Заработная плата сотрудника */
    private Double salary;

    /** Коэффициент годового бонуса */
    private Double bonus;

    /** Количество отработанных дней */
    private Integer workDays;

    /** Идентификатор коммуникации */
    @Min(value = 1, message = "communicationId должен быть не меньше 1")
    @Max(value = 100000, message = "communicationId должен быть не больше 100000")
    private Integer communicationId;

    /** Идентификатор шаблона сообщения */
    private int templateId;

    /** Код продукта */
    private int productCode;

    /** Код SMS сообщения */
    private int smsCode;
}