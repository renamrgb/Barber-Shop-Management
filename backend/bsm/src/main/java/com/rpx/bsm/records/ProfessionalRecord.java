package com.rpx.bsm.records;

import javax.validation.Valid;
import java.util.Set;

public record ProfessionalRecord(
        @Valid UserRecord user,
        @Valid Set<ProcedimentoIdRecord> procedures
) {}
