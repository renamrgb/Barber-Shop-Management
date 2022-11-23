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
@Table(name = "procedimento")
@Getter
@Setter
@NoArgsConstructor
public class Procedimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String descricao;
    private Double valor;

    @JsonIgnore
    @ManyToMany(mappedBy = "procedimentos") //Nome do set
    private Set<Profissional> profissionais = new HashSet<>();

    public Procedimento(String descricao, Double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Procedimento that = (Procedimento) o;
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
