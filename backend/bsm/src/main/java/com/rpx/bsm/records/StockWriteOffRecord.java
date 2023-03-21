package com.rpx.bsm.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rpx.bsm.entities.Product;
import com.rpx.bsm.entities.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record StockWriteOffRecord(
        @NotNull User user,
        @NotNull Product product,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate record,
        @NotNull @Min(1) Integer qty,
        @NotBlank @Length(max = 255) String reason
) {
}
