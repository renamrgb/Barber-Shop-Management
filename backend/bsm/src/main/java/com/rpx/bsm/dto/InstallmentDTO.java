package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Installment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class InstallmentDTO {

    private Long id;
    private BigDecimal installmentValue;
    private LocalDate dueDate;

    public InstallmentDTO(Long id, BigDecimal installmentValue, LocalDate dueDate) {
        this.id = id;
        this.installmentValue = installmentValue;
        this.dueDate = dueDate;
    }

    public InstallmentDTO(Installment obj) {
        setId(obj.getId());
        setInstallmentValue(obj.getInstallmentValue());
        setDueDate(obj.getDueDate());
    }
}
