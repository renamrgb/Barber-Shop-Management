package com.rpx.bsm.dto;

import com.rpx.bsm.entities.ExpenseType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseTypeDTO {


    private Long Id;
    private String description;
    private Boolean isActive;


    public ExpenseTypeDTO(Long id, String description, Boolean isActive) {
        Id = id;
        this.description = description;
        this.isActive = isActive;
    }

    public  ExpenseTypeDTO(ExpenseType obj){
        setId(obj.getId());
        setDescription(obj.getDescription());
        setIsActive(obj.getIsActive());
    }

}
