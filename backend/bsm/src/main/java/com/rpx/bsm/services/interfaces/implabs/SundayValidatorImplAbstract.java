package com.rpx.bsm.services.interfaces.implabs;

import com.rpx.bsm.services.ParameterService;
import com.rpx.bsm.services.interfaces.AbstractDayValidator;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class SundayValidatorImplAbstract extends AbstractDayValidator {

    public SundayValidatorImplAbstract(ParameterService parameterService) {
        super(parameterService);
    }

    @Override
    protected String getErrorMessage() {
        return "Não é permitido realizar atendimentos no domingo";
    }

    @Override
    protected String getParameterkey() {
        return "WORKS_ON_SUNDAY";
    }

    @Override
    public boolean isForDayOfWeek(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SUNDAY;
    }
}
