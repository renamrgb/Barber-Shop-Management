package com.rpx.bsm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AddresDTO implements Serializable {

    private String zipCode;
    private String publicPlace;
    private String neighborhood;
    private String complement;
    private String city;
    private String state;

    public AddresDTO(String zipCode, String publicPlace, String neighborhood, String complement, String city, String state) {
    }
}

