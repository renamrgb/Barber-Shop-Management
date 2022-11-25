package com.rpx.bsm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "profissional")
@Getter
@Setter
@NoArgsConstructor
public class Profissional implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;

    private String nome;
    private String telefone;

    private Boolean ativo;

    @ManyToMany
    @JoinTable(name = "profissionaisProcedimentos",
            joinColumns = @JoinColumn(name = "profissional_id"),
            inverseJoinColumns = @JoinColumn(name = "procedimento_id")
    )
    private Set<Procedimento> procedimentos = new HashSet<>();

    public Profissional(String nome, String telefone, Boolean ativo) {
        this.nome = nome;
        this.telefone = telefone;
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profissional that = (Profissional) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "Profissional{" + "Id=" + Id + ", nome='" + nome + '\'' + ", telefone='" + telefone + '\'' + '}';
    }
}
