package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "modeloMensagem")
@Getter
@Setter
@NoArgsConstructor
public class ModeloMensagem  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String titulo;
    private String mensagem;
    private Boolean ativo;

    public ModeloMensagem(String titulo, String mensagem, Boolean ativo) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModeloMensagem that = (ModeloMensagem) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "ModeloMensagem{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", texto='" + mensagem + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
