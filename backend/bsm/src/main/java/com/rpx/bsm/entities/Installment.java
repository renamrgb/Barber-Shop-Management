package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "installment")
public class Installment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long id;
    private BigDecimal installmentValue;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private Double amountPaid;
    @ManyToOne(fetch = FetchType.EAGER)
    private PaymentMethod paymentMethod;

    @ManyToOne
    private Expense expense;
    public Installment(BigDecimal installmentValue, LocalDate dueDate) {
        this.installmentValue = installmentValue;
        this.dueDate = dueDate;
    }
    public Installment(LocalDate paymentDate, Double amountPaid, PaymentMethod paymentMethod) {
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
    }
    public Installment(Long id, BigDecimal installmentValue, LocalDate dueDate) {
        this.id = id;
        this.installmentValue = installmentValue;
        this.dueDate = dueDate;
    }
}
