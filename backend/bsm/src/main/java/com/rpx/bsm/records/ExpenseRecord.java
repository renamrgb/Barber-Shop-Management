package com.rpx.bsm.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rpx.bsm.entities.ExpenseType;
import com.rpx.bsm.entities.Installment;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public record ExpenseRecord(
        @NotBlank(message = "description é obrigatório") @Length(min = 2, max = 60)
        String description,
        @NotNull @DecimalMin(value = "0.1", message = "total precisa ser maior que 0.0") @Digits(integer = 10, fraction = 2)
        Double total,
        @NotNull @Min(value = 0, message = "daysBeetwenInstallments deve ser maior ou igual a 0")
        Integer daysBeetwenInstallments,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate releaseDate,
        @NotNull @Min(value = 0, message = "quantityOfInstallments deve ser maior ou igual a 0")
        Integer quantityOfInstallments,
        @NotNull
        ExpenseType expenseType,
        @Valid Set<Installment> installments
) {
}
