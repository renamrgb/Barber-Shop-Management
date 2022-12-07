package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ProdutoRecord(
        @NotBlank @Length(min = 2, max = 60) String titulo,
        @NotNull Double valor,
        @NotBlank @Length(min = 2, max = 50) String marca,
        @NotNull Integer quantidade,
        @NotNull Boolean ativo
) {
}
