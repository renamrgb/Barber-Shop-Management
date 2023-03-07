package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Installment;
import com.rpx.bsm.entities.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PaymentMethodDTO implements Serializable {

    private Long Id;
    private String description;
    private Boolean isActive;

    public PaymentMethodDTO(PaymentMethod obj){
        if(obj!=null){
            setId(obj.getId());
            setDescription(obj.getDescription());
            setIsActive(obj.getIsActive());
        }
    }

}
