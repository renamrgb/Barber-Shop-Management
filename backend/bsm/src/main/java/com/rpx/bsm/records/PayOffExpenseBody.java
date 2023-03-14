package com.rpx.bsm.records;

import com.rpx.bsm.entities.PaymentMethod;

public record PayOffExpenseBody(
        Double amountPaid,
        PaymentMethod paymentMethod
) { }
