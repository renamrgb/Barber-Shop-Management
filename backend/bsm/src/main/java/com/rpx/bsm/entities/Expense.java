package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expense")
public class Expense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;

    private String description;

    private Double total;

    private Integer daysBeetwenInstallments;

    private LocalDate releaseDate;

    private Integer quantityOfInstallments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;

    public Expense(String description, Double total, Integer daysBeetwenInstallments, LocalDate releaseDate, Integer quantityOfInstallments, ExpenseType expenseType) {
        this.description = description;
        this.total = total;
        this.daysBeetwenInstallments = daysBeetwenInstallments;
        this.releaseDate = releaseDate;
        this.quantityOfInstallments = quantityOfInstallments;
        this.expenseType = expenseType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(Id, expense.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "Id=" + Id +
                ", description='" + description + '\'' +
                ", total=" + total +
                ", daysBeetwenInstallments=" + daysBeetwenInstallments +
                ", releaseDate=" + releaseDate +
                ", quantityOfInstallments=" + quantityOfInstallments +
                ", expenseType=" + expenseType +
                '}';
    }
}
