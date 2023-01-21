package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ModeloMensagemRecord(
        @NotBlank @Length(min=2, max =60) String titulo,
        @NotBlank @Length(min=2, max =1000) String mensagem,
        @NotNull Boolean ativo
) {
}
