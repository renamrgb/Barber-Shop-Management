package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserRecord(
        @NotBlank(message = "Nome é obrigatório") @Length(min = 2, max = 60) String name,
        @Email @Length(min = 2, max = 60) String email,
        @NotBlank(message = "Senha é obrigatório") @Length(min = 2, max = 10) String password,
        @Length(min = 11, max = 12) String phoneNumber,
        @Length(max = 15) String cpf,
        @Length(max = 11) String rg,
        @Valid AddresRecord address,
        @NotNull Boolean isActive
) {
}
