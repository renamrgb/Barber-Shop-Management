package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ExpenseTypeRecord(
        @NotBlank(message = "O campo 'descrição' é obrigatório") @Length(min = 2, max = 60, message = "O campo 'descrição' deve conter até 60 caracteres") String description,
        @NotNull(message = "O campo 'ativo' é obrigatório") Boolean isActive
) {
}
