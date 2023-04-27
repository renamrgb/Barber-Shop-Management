package com.rpx.bsm.records;

import com.rpx.bsm.entities.LoyaltyCard;

import javax.validation.Valid;

public record CustomerRecord(
        @Valid UserRecord user,
        LoyaltyCard loyaltyCard
) {
}
