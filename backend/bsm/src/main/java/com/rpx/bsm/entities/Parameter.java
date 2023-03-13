package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "parameter")
@Getter
@Setter
public class Parameter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    @OneToMany(mappedBy = "parameter", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParameterValue> parameterValues;
    @OneToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public Parameter() {
        this.parameterValues = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter that = (Parameter) o;
        return Objects.equals(Id, that.Id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
    @Override
    public String toString() {
        return "Parameters{" +
                "Id=" + Id +
                '}';
    }
}
