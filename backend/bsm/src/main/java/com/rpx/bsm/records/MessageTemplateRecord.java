package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record MessageTemplateRecord(
        @NotBlank() @Length(min=2, max =60, message = "O título deve conter de 2  à 60 caracteres") String title,
        @NotBlank @Length(min=2, max =1000, message = "A mensagem deve conter de 2  à 1000 caracteres") String body,
        @NotNull Boolean isActive
) {
}
