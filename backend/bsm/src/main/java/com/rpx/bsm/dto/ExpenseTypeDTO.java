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
    private Boolean generateInstallments;
    private Boolean isActive;

    public ExpenseTypeDTO(Long id, String description, Boolean generateInstallments, Boolean isActive) {
        Id = id;
        this.description = description;
        this.generateInstallments = generateInstallments;
        this.isActive = isActive;
    }

    public  ExpenseTypeDTO(ExpenseType obj){
        setId(obj.getId());
        setDescription(obj.getDescription());
        setGenerateInstallments(obj.getGenerateInstallments());
        setIsActive(obj.getIsActive());
    }

}
