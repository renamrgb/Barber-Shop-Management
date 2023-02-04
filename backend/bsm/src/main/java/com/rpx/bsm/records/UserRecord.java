package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserRecord(
        @NotBlank(message = "Nome é obrigatório") @Length(min = 2, max = 100) String name,
        @Email @Length(min = 2, max = 60) String email,
        @NotBlank(message = "Senha é obrigatório") @Length(min = 2, max = 10) String password,
        @Length(min = 2, max = 11) String phoneNumber,
        @Length(min = 2, max = 11) String cpf,
        @Length(min = 2, max = 11) String rg,
        @Valid AddresRecord addres,
        @NotNull Boolean isActive
) {
}
