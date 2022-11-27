package com.rpx.bsm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoDTO implements Serializable {

    private String cep;
    private String logradouro;
    private String bairro;
    private String complemento;
    private String cidade;
    private String uf;

}

