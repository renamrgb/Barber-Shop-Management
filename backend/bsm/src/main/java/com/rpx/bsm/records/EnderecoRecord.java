package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

public record EnderecoRecord(
        @Length(max = 8) String cep,
        @Length(max = 60) String logradouro,
        @Length(max = 30) String bairro,
        @Length(max = 60) String complemento,
        @Length(max = 45) String cidade,
        @Length(max = 2) String uf
) {
}
