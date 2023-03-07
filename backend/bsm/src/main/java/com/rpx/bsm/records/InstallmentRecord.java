package com.rpx.bsm.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rpx.bsm.entities.PaymentMethod;

import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record InstallmentRecord(
        @NotNull @DecimalMin(value = "0.1", message = "installmentValue precisa ser maior que 0.0") @Digits(integer = 5, fraction = 2)
        Double installmentValue,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dueDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate paymentDate,
        @Digits(integer = 5, fraction = 2)
        Double amountPaid,
        PaymentMethod paymentMethod
) {
}
