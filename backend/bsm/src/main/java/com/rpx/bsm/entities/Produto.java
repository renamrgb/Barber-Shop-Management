package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String titulo;
    private Double valor;
    private String marca;
    private Integer quantidade;

    private Boolean ativo;

    public Produto(String titulo, Double valor, String marca, Integer quantidade, Boolean ativo) {
        this.titulo = titulo;
        this.valor = valor;
        this.marca = marca;
        this.quantidade = quantidade;
        this.ativo = ativo;
    }

    public Produto(Long id, String titulo, Double valor, String marca, Integer quantidade) {
        Id = id;
        this.titulo = titulo;
        this.valor = valor;
        this.marca = marca;
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(Id, produto.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "Procedimento{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", valor=" + valor +
                '}';
    }
}