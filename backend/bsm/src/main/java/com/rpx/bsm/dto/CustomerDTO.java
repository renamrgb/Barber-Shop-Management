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
        address.setState(obj.getUser().getAddres().getState());
        address.setZipCode(obj.getUser().getAddres().getZipCode());
        address.setPublicPlace(obj.getUser().getAddres().getPublicPlace());
        address.setNeighborhood(obj.getUser().getAddres().getNeighborhood());
        address.setComplement(obj.getUser().getAddres().getComplement());
        address.setCity(obj.getUser().getAddres().getCity());
        isActive = obj.getUser().getIsActive();
    }

}
