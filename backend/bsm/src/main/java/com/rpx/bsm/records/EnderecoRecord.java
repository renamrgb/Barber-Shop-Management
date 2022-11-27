package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

public record EnderecoRecord(
        @Length(min = 2, max = 8) String cep,
        @Length(min = 2, max = 60) String logradouro,
        @Length(min = 2, max = 30) String bairro,
        @Length(min = 2, max = 60) String complemento,
        @Length(min = 2, max = 45) String cidade,
        @Length(min = 2, max = 2) String uf
) {
}
