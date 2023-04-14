package com.rpx.bsm.entities;

import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.records.CustomerRecord;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usertab_id")
    private User user;


    public Customer() {
        this.user = new User();
    }

    public Customer(User user) {
        this.user = user;
    }

    public Customer(CustomerRecord record) {
        this.user = new User(record.user());
        this.user.getAccessLevel().setAuthority(NivelAcessoEnum.ROLE_USER);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Id=" + Id +
                ", usuario=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer cliente = (Customer) o;
        return Objects.equals(Id, cliente.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
