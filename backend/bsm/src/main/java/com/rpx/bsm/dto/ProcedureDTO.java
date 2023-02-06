package com.rpx.bsm.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.rpx.bsm.entities.Procedure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcedureDTO {
    private Long id;

    private String description;
    private Double price;

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
        setIsActive(obj.getIsActive());
    }
}
