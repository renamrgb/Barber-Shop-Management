package com.rpx.bsm.records;

import com.rpx.bsm.entities.ProductProcedureType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ProcedureRecord(
        @NotBlank(message = "O campo 'descrição' é obrigatório")
        @Length(max = 60)
        String description,
        @Digits(integer = 3, fraction = 2)
        @DecimalMin(value = "0.1", message = "O campo 'preço' precisa ser maior que 0.0")
        @NotNull
        Double price,
        ProductProcedureType procedureType,
        @NotNull
        Boolean isActive
) {}
