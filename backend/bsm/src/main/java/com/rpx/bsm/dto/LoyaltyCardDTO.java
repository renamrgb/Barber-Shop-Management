package com.rpx.bsm.dto;

import com.rpx.bsm.entities.LoyaltyCard;
import com.rpx.bsm.entities.Nfe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class LoyaltyCardDTO implements Serializable {
    private Long id;
    private Long points;
    private Long quantityUsed;

    public LoyaltyCardDTO(LoyaltyCard obj) {
        if(obj != null){
            setId(obj.getId());
            setPoints(obj.getPoints());
            setQuantityUsed(obj.getQuantityUsed());
        }
    }
}
