package com.rpx.bsm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product_procedure_type")
@Getter
@Setter
@NoArgsConstructor
public class ProductProcedureType implements Serializable {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String description;
    private Boolean isActive;
    @JsonIgnore
    @OneToMany(mappedBy = "productProcedureType", cascade = CascadeType.ALL)
    private Set<Product> products;
    @JsonIgnore
    @OneToMany(mappedBy = "productProcedureType", cascade = CascadeType.ALL)
    private Set<Procedure> procedures;

    public ProductProcedureType(Long id) {
        Id = id;
    }

    public ProductProcedureType(String description, Boolean isActive) {
        this.description = description;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductProcedureType that = (ProductProcedureType) o;
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
