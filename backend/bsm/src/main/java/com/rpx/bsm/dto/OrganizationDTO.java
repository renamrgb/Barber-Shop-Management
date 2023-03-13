package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Address;
import com.rpx.bsm.entities.Organization;
import com.rpx.bsm.entities.Parameter;
import com.rpx.bsm.entities.ParameterValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrganizationDTO {
    private Long Id;
    private String name;
    private String cnpj;
    private AddressDTO address;

    public OrganizationDTO(Organization obj){
        setId(obj.getId());
        setName(obj.getName());
        setCnpj(obj.getCnpj());
        if(obj.getAddress() != null)
            setAddress(new AddressDTO(obj.getAddress()));
    }

}
