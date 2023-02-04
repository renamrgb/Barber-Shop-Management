package com.rpx.bsm.records;

import javax.validation.Valid;
import java.util.Set;

public record ProfissionalRecord(
        @Valid UserRecord usuario,
        @Valid Set<ProcedimentoIdRecord> procedimentos
) {}
