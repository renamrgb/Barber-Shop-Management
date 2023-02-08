package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "expense_type")
@Getter
@Setter
@NoArgsConstructor
public class ExpenseType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String description;
    private Boolean isActive;

    @OneToMany(mappedBy = "expenseType", cascade = CascadeType.ALL)
    private Set<Expense> expenses;

    public ExpenseType(String description, Boolean isActive) {
        this.description = description;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseType that = (ExpenseType) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "TipoDespesa{" + "descricao='" + description + '\'' + ", ativo=" + isActive + '}';
    }
}
