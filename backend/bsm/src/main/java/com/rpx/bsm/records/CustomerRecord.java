package com.rpx.bsm.records;

import javax.validation.Valid;

public record CustomerRecord(
        @Valid UserRecord usuario
) {
}
