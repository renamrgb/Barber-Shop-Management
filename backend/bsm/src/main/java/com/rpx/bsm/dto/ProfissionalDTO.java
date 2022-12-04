package com.rpx.bsm.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProfissionalDTO implements Serializable {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String rg;

    private EnderecoDTO endereco;
    private Boolean ativo;

    public ProfissionalDTO() {
        this.endereco = new EnderecoDTO();
    }
}
