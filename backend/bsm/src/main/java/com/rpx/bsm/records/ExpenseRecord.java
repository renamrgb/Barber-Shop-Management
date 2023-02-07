package com.rpx.bsm.records;

import com.rpx.bsm.entities.ExpenseType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

public record ExpenseRecord(
        @NotBlank(message = "description é obrigatório") @Length(min = 2, max = 60)
        String description,
        @NotNull @DecimalMin(value = "0.1", message = "total precisa ser maior que 0.0") @Digits(integer = 3, fraction = 2)
        Double total,
        @NotNull @Min(value = 0, message = "daysBeetwenInstallments deve ser maior ou igual a 0")
        Integer daysBeetwenInstallments,
        LocalDate releaseDate,
        @NotNull @Min(value = 0, message = "quantityOfInstallments deve ser maior ou igual a 0")
        Integer quantityOfInstallments,
        @NotNull
        ExpenseType expenseType

) {
}
