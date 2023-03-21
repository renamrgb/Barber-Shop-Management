package com.rpx.bsm.records;

import org.hibernate.validator.constraints.Length;

public record AddresRecord(
        @Length(max = 8) String zipCode,
        @Length(max = 60) String publicPlace,
        @Length(max = 30) String neighborhood,
        @Length(max = 60) String complement,
        @Length(max = 45) String city,
        @Length(max = 2) String state
) {
}
