package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Installment;
import com.rpx.bsm.entities.PaymentMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class InstallmentDTO implements Comparable<InstallmentDTO> {

    private Long id;
    private BigDecimal installmentValue;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private Double amountPaid;
    private PaymentMethodDTO paymentMethod;
    private Boolean paid;

    public InstallmentDTO(Long id, BigDecimal installmentValue, LocalDate dueDate) {
        this.id = id;
        this.installmentValue = installmentValue;
        this.dueDate = dueDate;
    }

    public InstallmentDTO(Installment obj) {
        setId(obj.getId());
        setInstallmentValue(obj.getInstallmentValue());
        setDueDate(obj.getDueDate());
        setPaymentDate(obj.getPaymentDate());
        setAmountPaid(obj.getAmountPaid());
        setPaymentMethod(new PaymentMethodDTO(obj.getPaymentMethod()));
        setPaid(obj.getPaid());
    }
    @Override
    public int compareTo(InstallmentDTO otherInstallment) {
        return this.id.compareTo(otherInstallment.getId());
    }
}
