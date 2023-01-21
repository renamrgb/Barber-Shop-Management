package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ExpenseTypeRecord(
        @NotBlank @Length(min = 2, max = 60) String description,
        @NotNull Boolean isActive
) {
}
