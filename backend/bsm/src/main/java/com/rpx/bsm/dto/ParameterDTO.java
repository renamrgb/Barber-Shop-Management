package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Organization;
import com.rpx.bsm.entities.Parameter;
import com.rpx.bsm.entities.ParameterValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ParameterDTO {
    private Long Id;
    private List<ParameterValueDTO> parameterValues;
    private OrganizationDTO organization;

    public ParameterDTO(Parameter obj){
        this.parameterValues = new ArrayList<>();
        for(ParameterValue e : obj.getParameterValues()){
            this.parameterValues.add(new ParameterValueDTO(e));
        }
        setOrganization(new OrganizationDTO(obj.getOrganization()));
        setId(obj.getId());
    }

}
