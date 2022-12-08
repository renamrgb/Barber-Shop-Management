package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Procedimento;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    private Set<ProcedimentoDTO> procedimentos = new HashSet<>();
    private Boolean ativo;

    public ProfissionalDTO() {
        this.endereco = new EnderecoDTO();
    }
}
