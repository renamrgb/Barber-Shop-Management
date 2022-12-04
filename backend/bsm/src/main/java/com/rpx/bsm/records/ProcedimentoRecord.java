package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ProcedimentoRecord(
        Long id,
        @NotBlank(message = "Descrição é um campo obrigatório")
        @Length(min = 2, max = 60)
        String descricao,
        @Digits(integer = 3, fraction = 2)
        @NotBlank(message = "Valor é um campo obrigatório")
        Double valor,
        @NotBlank(message = "Ativo é um campo obrigatório")
        @NotNull Boolean ativo
) {
}
