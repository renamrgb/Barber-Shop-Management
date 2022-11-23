package com.rpx.bsm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private String nome;
    private String telefone;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToOne(mappedBy = "profissional", cascade = CascadeType.ALL)
    private Usuario usuarioProf;

    private String cep;
    private String endreço;
    private String bairro;
    private String complemento;
    private String cidade;

    public Cliente(String nome, String telefone, Usuario usuario, Usuario usuarioProf, String cep, String endreço, String bairro, String complemento, String cidade) {
        this.nome = nome;
        this.telefone = telefone;
        this.usuario = usuario;
        this.usuarioProf = usuarioProf;
        this.cep = cep;
        this.endreço = endreço;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Id=" + Id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
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
