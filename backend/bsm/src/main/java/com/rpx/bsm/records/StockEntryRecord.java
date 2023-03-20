package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;

public record StockEntryRecord(
        @NotBlank @Length(min = 2, max = 60)
        String supplier,
        @NotNull
        List<StockEntryProductRecord> products,
        NfeRecord nfe
) { }
