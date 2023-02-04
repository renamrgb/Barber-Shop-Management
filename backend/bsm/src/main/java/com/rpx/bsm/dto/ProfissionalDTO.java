package com.rpx.bsm.dto;

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
    private String cpf;
    private String rg;

    private AddresDTO addres;
    private Set<ProcedimentoDTO> procedimentos = new HashSet<>();
    private Boolean ativo;

    public ProfissionalDTO() {
        this.addres = new AddresDTO();
    }
}
