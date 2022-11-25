package com.rpx.bsm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    @NonNull
    private String nome;
    private String telefone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    public Cliente(@NonNull String nome, String telefone, Usuario usuario, Usuario usuarioProf) {
        this.nome = nome;
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Id=" + Id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'';
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
