package com.rpx.bsm.dto;

import com.rpx.bsm.entities.ProductProcedureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ProductProcedureTypeDTO implements Serializable {

    private Long Id;
    private String description;
    private Boolean isActive;

    public ProductProcedureTypeDTO(ProductProcedureType obj){
        setId(obj.getId());
        setDescription(obj.getDescription());
        setIsActive(obj.getIsActive());
    }

}
