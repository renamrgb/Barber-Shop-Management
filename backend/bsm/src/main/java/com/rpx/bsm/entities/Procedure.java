package com.rpx.bsm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rpx.bsm.records.ProcedureRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "procedure")
@Getter
@Setter
@NoArgsConstructor
public class Procedure implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    @Column(nullable = false, columnDefinition = "VARCHAR(60)")
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Boolean isActive;
    @ManyToOne
    private ProductProcedureType productProcedureType;
    @JsonIgnore
    @ManyToMany(mappedBy = "procedures") //Nome do set
    private Set<Professional> professionals = new HashSet<>();

    public Procedure(Long id) {
        Id = id;
    }

    public Procedure(ProcedureRecord record){
        setDescription(record.description());
        setPrice(record.price());
        setProductProcedureType(record.procedureType());
        setIsActive(record.isActive());
    }

    public Procedure(String description, Double price, Boolean isActive, Set<Professional> professionals) {
        this.description = description;
        this.price = price;
        this.isActive = isActive;
        this.professionals = professionals;
    }

    public Procedure(String description, Double price, Boolean isActive) {
        this.description = description;
        this.price = price;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Procedure that = (Procedure) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "Id=" + Id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isActive=" + isActive +
                ", professionals=" + professionals +
                '}';
    }
}
