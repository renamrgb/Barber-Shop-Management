package com.rpx.bsm.services.interfaces.impl;

import com.rpx.bsm.entities.ParameterValue;
import com.rpx.bsm.services.ParameterService;
import com.rpx.bsm.services.interfaces.DayValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@RequiredArgsConstructor
@Component
public class SaturdayValidator implements DayValidator {

    private final ParameterService parameterService;

    @Override
    public void validateDay() {
        ParameterValue worksOnMonday = parameterService.findByPameterKey("WORKS_ON_SATURDAY");
        validParameterIsFalse(worksOnMonday, "Não é permitido realizar atendimentos no sábado");
    }

    @Override
    public boolean isForDayOfWeek(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY;
    }
}
