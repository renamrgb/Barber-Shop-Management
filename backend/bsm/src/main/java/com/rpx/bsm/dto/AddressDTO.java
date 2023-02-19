package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO implements Serializable {

    private String zipCode;
    private String publicPlace;
    private String neighborhood;
    private String complement;
    private String city;
    private String state;

    public  AddressDTO(Address obj) {
        setZipCode(obj.getZipCode());
        setPublicPlace(obj.getPublicPlace());
        setNeighborhood(obj.getNeighborhood());
        setComplement(obj.getComplement());
        setCity(obj.getCity());
        setState(obj.getState());
    }

    public  AddressDTO(ViaCEPResponse obj) {
        setZipCode(obj.getCep());
        setPublicPlace(obj.getLogradouro());
        setNeighborhood(obj.getBairro());
        setComplement(obj.getComplemento());
        setCity(obj.getLocalidade());
        setState(obj.getUf());
    }
}

