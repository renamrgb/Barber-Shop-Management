package com.rpx.bsm.services.interfaces.implabs;

import com.rpx.bsm.services.ParameterService;
import com.rpx.bsm.services.interfaces.AbstractDayValidator;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class MondayValidatorImplAbstract extends AbstractDayValidator {

    public MondayValidatorImplAbstract(ParameterService parameterService) {
        super(parameterService);
    }

    @Override
    protected String getErrorMessage() {
        return "Não é permitido realizar atendimentos no segunda-feira";
    }

    @Override
    protected String getParameterkey() {
        return "WORKS_ON_MONDAY";
    }

    @Override
    public boolean isForDayOfWeek(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.MONDAY;
    }
}
