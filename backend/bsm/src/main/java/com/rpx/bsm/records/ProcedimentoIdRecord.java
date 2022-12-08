package com.rpx.bsm.records;

import javax.validation.constraints.NotNull;

public record ProcedimentoIdRecord(
        @NotNull Long id
) {}
