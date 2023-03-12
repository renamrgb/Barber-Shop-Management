package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "organization")
@Getter
@Setter
@NoArgsConstructor
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String name;
    private String cnpj;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    public Organization(String name, String cnpj, Address address) {
        this.name = name;
        this.cnpj = cnpj;
        this.address = address;
    }

}
