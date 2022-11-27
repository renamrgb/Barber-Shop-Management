package com.rpx.bsm.records;

public record EnderecoRecord(
        String cep,
        String logradouro,
        String bairro,
        String complemento,
        String cidade,
        String uf
) {
}
