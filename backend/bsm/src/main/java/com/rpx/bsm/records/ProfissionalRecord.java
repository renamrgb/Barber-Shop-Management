package com.rpx.bsm.records;

import javax.validation.Valid;
import java.util.Set;

public record ProfissionalRecord(
        @Valid UsuarioRecord usuario,
        @Valid Set<ProcedimentoIdRecord> procedimentos
) {}
