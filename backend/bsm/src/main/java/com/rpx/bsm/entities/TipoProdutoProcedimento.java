package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tipoProdutoProcedimento")
@Getter
@Setter
@NoArgsConstructor
public class TipoProdutoProcedimento implements Serializable {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String descricao;
    private Boolean ativo;


    public TipoProdutoProcedimento(String descricao, Boolean ativo) {
        this.descricao = descricao;
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoProdutoProcedimento that = (TipoProdutoProcedimento) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "TipoProdutoProcedimento{" +
                "Id=" + Id +
                ", descricao='" + descricao + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
