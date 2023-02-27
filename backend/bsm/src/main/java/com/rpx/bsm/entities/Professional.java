package com.rpx.bsm.entities;

import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.records.ProcedimentoIdRecord;
import com.rpx.bsm.records.ProfessionalRecord;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "professional")
@Getter
@Setter
public class Professional implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usertab_id")
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "professional_procedures",
            joinColumns = @JoinColumn(name = "professional_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private Set<Procedure> procedures = new HashSet<>();

    public Professional() {
        this.user = new User();
    }
    public Professional(ProfessionalRecord record){
        this.user = new User(record.user());
        this.user.getAccessLevel().setAuthority(NivelAcessoEnum.ROLE_ADMIN);
        if(record.procedures() != null){
            this.procedures = new HashSet<>();
            for (ProcedimentoIdRecord p : record.procedures()) {
                this.getProcedures().add(new Procedure(p.id()));
            }
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professional that = (Professional) o;
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
                ", procedimentos=" + procedures  +
                '}';
    }
}
