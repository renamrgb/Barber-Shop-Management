package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Endereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ClienteDTO  implements Serializable {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String rg;
    private EnderecoDTO endereco;
    private Boolean ativo;
    public ClienteDTO() {
        this.endereco = new EnderecoDTO();
    }
}
