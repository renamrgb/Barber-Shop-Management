package com.rpx.bsm.records.util;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter
public class WorkSchedule {
    private LocalTime starTime;
    private LocalTime lunchStartTime;
    private LocalTime lunchEndTime;
    private LocalTime endTime;

}
