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

//    @NotBlank(message = "UID не может быть пустым")
    @Size(max = 32, message = "UID не должен превышать 32 символа")
    private String uid;

//    @NotBlank(message = "Operation UID не может быть пустым")
    @Size(max = 32, message = "Operation UID не должен превышать 32 символа")
    private String operationUid;

//    @NotNull(message = "Имя системы не может быть пустым")
    private Systems systemName;

//    @NotBlank(message = "Системное время не может быть пустым")
    private String systemTime;

//    @NotBlank(message = "Источник не может быть пустым")
    private String source;

    @Min(value = 1, message = "communicationId должен быть не меньше 1")
    @Max(value = 100000, message = "communicationId должен быть не больше 100000")
    private int communicationId;

    private int templateId;
    private int productCode;
    private int smsCode;

    @Override
    public String toString() {
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName=" + systemName + " ('" + systemName.getDescription() + "')" +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                '}';
    }
}