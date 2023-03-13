package com.rpx.bsm.entities;

import com.rpx.bsm.repositories.ParameterValueRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "parameter_value")
@NoArgsConstructor
@Getter
@Setter
public class ParameterValue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String parameter_name;
    private String parameter_key;
    private String parameter_value;
    @ManyToOne
    @JoinColumn(name = "parameter_id")
    private Parameter parameter;

    public ParameterValue(String parameter_key, String parameter_value, Parameter parameter) {
        this.parameter_key = parameter_key;
        this.parameter_value = parameter_value;
        this.parameter = parameter;
    }

    public ParameterValue(String parameter_key, String parameter_value) {
        this.parameter_key = parameter_key;
        this.parameter_value = parameter_value;
    }

    public ParameterValue(String parameter_name, String parameter_key, String parameter_value, Parameter parameter) {
        this.parameter_name = parameter_name;
        this.parameter_key = parameter_key;
        this.parameter_value = parameter_value;
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterValue that = (ParameterValue) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "ParameterValue{" +
                "Id=" + Id +
                ", parameter_key='" + parameter_key + '\'' +
                ", parameter_value='" + parameter_value + '\'' +
                ", parameter=" + parameter +
                '}';
    }
}
