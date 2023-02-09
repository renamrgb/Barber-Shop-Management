package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
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
    private Double installmentValue;
    private LocalDate dueDate;
    @ManyToOne
    private Expense expense;
    public Installment(Double installmentValue, LocalDate dueDate) {
        this.installmentValue = installmentValue;
        this.dueDate = dueDate;
    }

    public Installment(Long id, Double installmentValue, LocalDate dueDate) {
        this.id = id;
        this.installmentValue = installmentValue;
        this.dueDate = dueDate;
    }
}
