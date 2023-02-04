package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
public class Addres implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Integer id;
    private String zipCode;
    private String publicPlace;
    private String neighborhood;
    private String complement;
    private String city;
    private String state;

    public Addres(String zipCode, String publicPlace, String neighborhood, String complement, String city, String state) {
        this.zipCode = zipCode;
        this.publicPlace = publicPlace;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.city = city;
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Addres endereco = (Addres) o;
        return Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Addres{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", publicPlace='" + publicPlace + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", complement='" + complement + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
