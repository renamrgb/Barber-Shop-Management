package com.rpx.bsm.records;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public record NfeRecord(
        @NotNull @DecimalMin(value = "0.1") @Digits(integer = 10, fraction = 2)
        Double valueNfe,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dateofPurchase,
        String numberNfe,
        String cfop,
        String key
) {
}
