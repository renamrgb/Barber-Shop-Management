package com.rpx.bsm.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rpx.bsm.entities.Professional;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record BlockedTimesRecord(
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
        @Length(max = 60) @NotBlank String description, @NotNull Professional professional) {
}
