package com.rpx.bsm.services.interfaces;

import com.rpx.bsm.entities.ParameterValue;
import com.rpx.bsm.resources.exceptions.DefaultErrorException;
import com.rpx.bsm.services.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@RequiredArgsConstructor
@Component
public abstract class AbstractDayValidator {

    private final ParameterService parameterService;

    public final void validateDay() {
        ParameterValue parameterValue = parameterService.findByPameterKey(getParameterkey());
        this.validParameterIsFalse(parameterValue, getErrorMessage());
    }

    private void validParameterIsFalse(final ParameterValue parameter, final String errorMessage) {
        if (parameter.getParameter_value().equals("false"))
            throw new DefaultErrorException(errorMessage);
    }

    protected abstract String getErrorMessage();
    protected abstract String getParameterkey();
    public abstract boolean isForDayOfWeek(DayOfWeek dayOfWeek);
}
