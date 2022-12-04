package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UsuarioRecord(
        @NotBlank(message = "Nome é obrigatório") @Length(min = 2, max = 60) String nome,
        @Email @Length(min = 2, max = 60) String email, @NotBlank(message = "Senha é obrigatório") @Length(min = 2, max = 10) String senha,
        @Length(min = 2, max = 11) String telefone,
        @Length(min = 2, max = 11) String cpf,
        @Length(min = 2, max = 11) String rg,
        @Valid EnderecoRecord endereco,
        @NotNull Boolean ativo
) {
}
