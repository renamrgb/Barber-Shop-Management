package com.rpx.bsm.services.interfaces.implabs;

import com.rpx.bsm.services.ParameterService;
import com.rpx.bsm.services.interfaces.AbstractDayValidator;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class SaturdayValidatorImplAbstract extends AbstractDayValidator {

    public SaturdayValidatorImplAbstract(ParameterService parameterService) {
        super(parameterService);
    }

    @Override
    protected String getErrorMessage() {
        return "Não é permitido realizar atendimentos no sábado";
    }

    @Override
    protected String getParameterkey() {
        return "WORKS_ON_SATURDAY";
    }

    @Override
    public boolean isForDayOfWeek(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY;
    }
}
