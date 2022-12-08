package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "cliente")
@Getter
@Setter
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    public Cliente() {
        this.usuario = new Usuario();
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Id=" + Id +
                ", usuario=" + usuario +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(Id, cliente.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
