package com.rpx.bsm.records;

import com.rpx.bsm.entities.Organization;
import com.rpx.bsm.entities.ParameterValue;

import java.util.List;

public record ParameterRecord (
        List<ParameterValue> parameterValues,
        Organization organization
) {
}
