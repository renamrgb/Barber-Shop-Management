package com.rpx.bsm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "procedimento")
@Getter
@Setter
@NoArgsConstructor
public class Procedure implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    @NonNull
    private String description;
    @NonNull
    private Double price;
    @NonNull
    private Boolean isActrive;

    @JsonIgnore
    @ManyToMany(mappedBy = "procedimentos") //Nome do set
    private Set<Profissional> profissionais = new HashSet<>();

    public Procedure(Long id) {
        Id = id;
    }

    public Procedure(String descricao, Double valor, Boolean ativo) {
        this.descricao = descricao;
        this.valor = valor;
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Procedure that = (Procedure) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "Procedimento{" + "Id=" + Id + ", descricao='" + descricao + '\'' + ", valor=" + valor + '}';
    }
}
