package com.rpx.bsm.entities;

import lombok.Getter;
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
public class Profissional implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private Boolean ativo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profissionaisProcedimentos",
            joinColumns = @JoinColumn(name = "profissional_id"),
            inverseJoinColumns = @JoinColumn(name = "procedimento_id")
    )
    private Set<Procedimento> procedimentos = new HashSet<>();

    public Profissional() {
        this.usuario = new Usuario();
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
        return "Profissional{" +
                "Id=" + Id +
                ", ativo=" + ativo +
                ", procedimentos=" + procedimentos +
                '}';
    }
}
