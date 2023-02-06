package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Customer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CustomerDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String cpf;
    private String rg;
    private AddressDTO address;
    private Boolean isActive;

    public CustomerDTO() {
        this.address = new AddressDTO();
    }

    public CustomerDTO(Customer obj) {
        this.address = new AddressDTO();
        id = obj.getId();
        name = obj.getUser().getName();
        email = obj.getUser().getEmail();
        phoneNumber = obj.getUser().getPhoneNumber();
        cpf = obj.getUser().getCpf();
        rg = obj.getUser().getRg();
        address.setState(obj.getUser().getAddress().getState());
        address.setZipCode(obj.getUser().getAddress().getZipCode());
        address.setPublicPlace(obj.getUser().getAddress().getPublicPlace());
        address.setNeighborhood(obj.getUser().getAddress().getNeighborhood());
        address.setComplement(obj.getUser().getAddress().getComplement());
        address.setCity(obj.getUser().getAddress().getCity());
        isActive = obj.getUser().getIsActive();
    }

}
