package com.rpx.bsm.entities;

import com.rpx.bsm.records.ExpenseRecord;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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
    @ManyToOne
    private ExpenseType expenseType;
    @OneToMany(mappedBy = "expense", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Installment> installments;
    //@OneToMany(mappedBy = "expenseStockEntry")
    //private List<StockEntry> stockEntries;

    public Expense() {
        installments = new ArrayList<>();
    }

    public Expense(String description, Double total, Integer daysBeetwenInstallments, LocalDate releaseDate, Integer quantityOfInstallments, ExpenseType expenseType) {
        this.description = description;
        this.total = total;
        this.daysBeetwenInstallments = daysBeetwenInstallments;
        this.releaseDate = releaseDate;
        this.quantityOfInstallments = quantityOfInstallments;
        this.expenseType = expenseType;
        this.setInstallments(new ArrayList<>());
    }

    public Expense(ExpenseRecord record) {
        this.description = record.description();
        this.total = record.total();
        this.daysBeetwenInstallments = record.daysBeetwenInstallments();
        this.releaseDate = record.releaseDate();
        this.quantityOfInstallments = record.quantityOfInstallments();
        this.expenseType = record.expenseType();
        this.installments = record.installments();
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
