package com.rpx.bsm.dto;

import com.rpx.bsm.entities.PaymentSchedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
public class PaymentScheduleDTO implements Serializable {
    private Long Id;
    private Double amount;
    private Double grossvalue;
    private Double discount;
    private PaymentMethodDTO paymentMethod;
    public PaymentScheduleDTO(PaymentSchedule obj) {
        if(obj != null){
            setId(obj.getId());
            setDiscount(obj.getDiscount());
            setGrossvalue(obj.getGrossvalue());
            setAmount(obj.getAmount());
            setPaymentMethod(new PaymentMethodDTO(obj.getPaymentMethod()));
        }
    }
}
