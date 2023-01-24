package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ProductRecord(
        @NotBlank(message = "O campo 'título' é obrigatório") @Length(min = 2, max = 60, message = "O campo 'título' deve conter até 60 caracteres") String title,
        @NotNull @DecimalMin(value = "0.1", message = "O campo 'valor' precisa ser maior que 0.0") Double price,
        @Length(max = 50, message = "O campo 'marca' deve conter até 60 caracteres") String brand,
        @NotNull @Min(value = 0, message = "O campo 'quantidade' deve ser maior ou igual a 0") Integer quantity,
        @NotNull Boolean isActive
) {
}
