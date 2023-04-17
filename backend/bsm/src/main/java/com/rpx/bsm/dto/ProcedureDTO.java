package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.entities.ProductProcedureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ProcedureDTO {
    private Long id;
    private String description;
    private Double price;
    private ProductProcedureType procedureType;
    private Boolean isActive;
    public LocalTime duration;
    public ProcedureDTO(Long id) {
        this.id = id;
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
        setDuration(obj.getDuration());
        setIsActive(obj.getIsActive());
    }
}
