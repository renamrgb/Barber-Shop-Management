package com.rpx.bsm.records;

import com.rpx.bsm.entities.Product;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public record StockEntryProductRecord(
        Product product,
        Integer quantity,
        @NotBlank @Length(min = 2, max = 60)
        Double price

) { }
