package com.rpx.bsm.entities;

import com.rpx.bsm.enums.NivelAcessoEnum;

import javax.persistence.*;

@Entity
@Table(name = "nivelAcesso")
public class NivelAcesso {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NivelAcessoEnum authority;

    public NivelAcesso() {
    }

    public NivelAcesso(NivelAcessoEnum authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NivelAcessoEnum getAuthority() {
        return authority;
    }

    public void setAuthority(NivelAcessoEnum authority) {
        this.authority = authority;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NivelAcesso other = (NivelAcesso) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
