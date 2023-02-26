package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.entities.Professional;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProfissionalDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String typePerson;
    private String document;
    private String rg;
    private AddressDTO address;
    private Set<ProcedureDTO> procedures = new HashSet<>();
    private Boolean isActive;

    public ProfissionalDTO() {
        this.address = new AddressDTO();
    }

    public ProfissionalDTO(Professional obj) {
        this.address = new AddressDTO();
        id = obj.getId();
        name = obj.getUser().getName();
        email = obj.getUser().getEmail();
        typePerson = obj.getUser().getTypePerson();
        phoneNumber = obj.getUser().getPhoneNumber();
        document = obj.getUser().getDocument();
        rg = obj.getUser().getRg();
        address.setState(obj.getUser().getAddress().getState());
        address.setZipCode(obj.getUser().getAddress().getZipCode());
        address.setPublicPlace(obj.getUser().getAddress().getPublicPlace());
        address.setNeighborhood(obj.getUser().getAddress().getNeighborhood());
        address.setComplement(obj.getUser().getAddress().getComplement());
        address.setCity(obj.getUser().getAddress().getCity());
        isActive = obj.getUser().getIsActive();
        for(Procedure p : obj.getProcedures()) {
            procedures.add(new ProcedureDTO(p));
        }
    }
}
