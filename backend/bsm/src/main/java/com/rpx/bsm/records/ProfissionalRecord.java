package com.rpx.bsm.records;

import javax.validation.Valid;

public record ProfissionalRecord(
        @Valid UsuarioRecord usuario,
        @Valid ProcedimentoRecord procedimentos
) {

}
