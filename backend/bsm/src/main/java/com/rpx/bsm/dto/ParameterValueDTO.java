package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Parameter;
import com.rpx.bsm.entities.ParameterValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class ParameterValueDTO {

    private Long Id;
    private String parameter_name;
    private String parameter_key;
    private String parameter_value;

    public ParameterValueDTO(ParameterValue obj) {
        setId(obj.getId());
        setParameter_name(obj.getParameter_name());
        setParameter_value(obj.getParameter_value());
        setParameter_key(obj.getParameter_key());
    }
}
