package com.rpx.bsm.records;

public record UsuarioRecord(
        String nome,
        String email,
        String senha,
        String telefone,
        String cpf,
        String rg,
        EnderecoRecord endereco
) {
}
