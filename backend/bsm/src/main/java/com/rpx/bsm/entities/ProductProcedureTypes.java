package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_procedure_types")
@Getter
@Setter
@NoArgsConstructor
public class ProductProcedureTypes implements Serializable {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String description;
    private Boolean isActive;


    public ProductProcedureTypes(String description, Boolean isActive) {
        this.description = description;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductProcedureTypes that = (ProductProcedureTypes) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "TipoProdutoProcedimento{" +
                "Id=" + Id +
                ", descricao='" + description + '\'' +
                ", ativo=" + isActive +
                '}';
    }
}
