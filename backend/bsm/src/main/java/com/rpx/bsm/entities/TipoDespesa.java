package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tipoDespesa")
@Getter
@Setter
@NoArgsConstructor
public class TipoDespesa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String descricao;
    private Boolean ativo;


    public TipoDespesa(String descricao, Boolean ativo) {
        this.descricao = descricao;
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoDespesa that = (TipoDespesa) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "TipoDespesa{" + "descricao='" + descricao + '\'' + ", ativo=" + ativo + '}';
    }
}
