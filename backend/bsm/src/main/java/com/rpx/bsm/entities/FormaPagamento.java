package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "formaPagamento")
@Getter
@Setter
@NoArgsConstructor
public class FormaPagamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String descricao;
    private Boolean ativo;

    public FormaPagamento(String descricao, boolean ativo) {
        this.descricao = descricao;
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormaPagamento that = (FormaPagamento) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "FormaDePagamento{" + "Id=" + Id + ", descricao='" + descricao + '\'' + ", ativo=" + ativo + '}';
    }
}
