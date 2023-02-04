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
    private AddresDTO addres;
    private Boolean isActive;

    public CustomerDTO() {
        this.addres = new AddresDTO();
    }

    public CustomerDTO(Customer obj) {
        this.addres = new AddresDTO();
        id = obj.getId();
        name = obj.getUser().getName();
        email = obj.getUser().getEmail();
        phoneNumber = obj.getUser().getPhoneNumber();
        cpf = obj.getUser().getCpf();
        rg = obj.getUser().getRg();
        addres.setState(obj.getUser().getAddres().getState());
        addres.setZipCode(obj.getUser().getAddres().getZipCode());
        addres.setPublicPlace(obj.getUser().getAddres().getPublicPlace());
        addres.setNeighborhood(obj.getUser().getAddres().getNeighborhood());
        addres.setComplement(obj.getUser().getAddres().getComplement());
        addres.setCity(obj.getUser().getAddres().getCity());
        isActive = obj.getUser().getIsActive();
    }

}
