package com.rpx.bsm.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rpx.bsm.entities.PaymentMethod;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record PayOffExpenseBody(
        Double amountPaid,
        PaymentMethod paymentMethod,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate paymentDate
) { }
