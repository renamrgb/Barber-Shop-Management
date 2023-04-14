package com.rpx.bsm.records;

import com.rpx.bsm.entities.Customer;
import com.rpx.bsm.entities.Procedure;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ScheduleRecord(
        @NotNull
        Customer client,
        @NotNull
        LocalDateTime date,
        @NotNull
        Procedure procedure
) {}
