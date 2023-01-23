package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record MessageTemplateRecord(
        @NotBlank(message = "O campo 'título' é obrigatório") @Length(min=2, max =60, message = "O campo 'título' deve conter até 60 caracteres") String title,
        @NotBlank(message = "O campo 'corpo da mensagem' é obrigatorio") @Length(min=2, max =1000, message = "O campo 'corpo da mensagem' deve conter até 1000 caracteres") String body,
        @NotNull(message = "O campo 'ativo' é obrigatório") Boolean isActive
) {
}
