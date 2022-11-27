package com.rpx.bsm.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
    private Boolean admin;
    private Boolean ativo;
    private String telefone;
    private String cpf;
    private String rg;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nivelacesso_id")
    private NivelAcesso nivelAcesso;

    public Usuario() {
        this.endereco = new Endereco();
        this.nivelAcesso = new NivelAcesso();
    }

    public Usuario(String nome, String email, String senha, Boolean admin, Boolean ativo, String telefone, String cpf, String rg, Endereco endereco, NivelAcesso nivelAcesso) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.admin = admin;
        this.ativo = ativo;
        this.telefone = telefone;
        this.cpf = cpf;
        this.rg = rg;
        this.endereco = endereco;
        this.nivelAcesso = nivelAcesso;
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
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", admin=" + admin +
                ", ativo=" + ativo +
                ", telefone='" + telefone + '\'' +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", endereco=" + endereco +
                ", nivelAcesso=" + nivelAcesso +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(
                nivelAcesso.getAuthority().name()
        ));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
