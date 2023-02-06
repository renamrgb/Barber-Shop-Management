package com.rpx.bsm.dto;

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

    public AddressDTO(String zipCode, String publicPlace, String neighborhood, String complement, String city, String state) {
    }
}

