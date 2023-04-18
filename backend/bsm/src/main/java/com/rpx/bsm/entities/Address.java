package com.rpx.bsm.entities;

import com.rpx.bsm.records.AddresRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Integer id;
    private String zipCode;
    private String publicPlace;
    private String neighborhood;
    private String complement;
    private String city;
    private String state;

    public Address(String zipCode, String publicPlace, String neighborhood, String complement, String city, String state) {
        this.zipCode = zipCode;
        this.publicPlace = publicPlace;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.city = city;
        this.state = state;
    }

    public Address(AddresRecord r){
        setCity(r.city());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address endereco = (Address) o;
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
