package com.rpx.bsm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String nome;
    private String email;
    private String senha;
    private Boolean admin;
    private Boolean ativo;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


    public Usuario(Long id, String nome, String email, String senha, Boolean admin, Boolean ativo) {
        Id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.admin = admin;
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(Id, usuario.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "Usuario{" + "Id=" + Id + ", nome='" + nome + '\'' + ", email='" + email + '\'' + ", senha='" + senha + '\'' + ", admin=" + admin + ", ativo=" + ativo + '}';
    }
}
