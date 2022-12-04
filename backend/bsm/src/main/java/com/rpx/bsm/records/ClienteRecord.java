package com.rpx.bsm.records;

import javax.validation.Valid;

public record ClienteRecord(
        @Valid UsuarioRecord usuario
) {
}
