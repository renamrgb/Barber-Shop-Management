package com.rpx.bsm.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rpx.bsm.entities.Customer;
import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.entities.Professional;
import com.rpx.bsm.entities.ServiceItems;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record ScheduleRecord(
        @NotNull
        Customer client,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startDate,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime endDate,
        @NotNull
        Set<Procedure> procedures,
        @NotNull
        Professional professional,
        List<ServiceItems> products

) {}
