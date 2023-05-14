package com.rpx.bsm.services.interfaces;

import com.rpx.bsm.entities.ParameterValue;
import com.rpx.bsm.resources.exceptions.DefaultErrorException;

import java.time.DayOfWeek;

public interface DayValidator {
    void validateDay();
    boolean isForDayOfWeek(DayOfWeek dayOfWeek);

    default void validParameterIsFalse(final ParameterValue parameter, final String errorMessage) {
        if (parameter.getParameter_value().equals("false"))
            throw new DefaultErrorException(errorMessage);
    }
}
