package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.entities.ProductProcedureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProcedureDTO {
    private Long id;
    private String description;
    private Double price;
    private ProductProcedureType procedureType;
    private Boolean isActive;
    public ProcedureDTO(Long id) {
        this.id = id;
    }

    public ProcedureDTO(Long id, String description, Double price, Boolean isActive) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.isActive = isActive;
    }

    public ProcedureDTO(Procedure obj){
        setId(obj.getId());
        setDescription(obj.getDescription());
        setPrice(obj.getPrice());
        if(obj.getProductProcedureType() != null){
            setProcedureType(obj.getProductProcedureType());
        }else{
            setProcedureType(new ProductProcedureType(0L));
        }
        setIsActive(obj.getIsActive());
    }
}
