package com.rpx.bsm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rpx.bsm.records.NfeRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "nfe")
public class Nfe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long id;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "stock_entry_id")
    private StockEntry stockEntry;
    private LocalDate dateofPurchase;
    private String numberNfe;
    private String cfop;
    private Double valueNfe;
    private String keyNfe;

    public Nfe(LocalDate dateofPurchase, String numberNfe, String cfop, Double valueNfe, String keyNfe) {
        this.dateofPurchase = dateofPurchase;
        this.numberNfe = numberNfe;
        this.cfop = cfop;
        this.valueNfe = valueNfe;
        this.keyNfe = keyNfe;
    }

    public Nfe(NfeRecord r, StockEntry stockEntry) {
        setDateofPurchase(r.dateofPurchase());
        setNumberNfe(r.numberNfe());
        setCfop(r.cfop());
        setValueNfe(r.valueNfe());
        setKeyNfe(r.key());
        setStockEntry(stockEntry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nfe nfe = (Nfe) o;
        return Objects.equals(id, nfe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Nfe{" +
                "id=" + id +
                ", stockEntry=" + stockEntry +
                ", dateofPurchase=" + dateofPurchase +
                ", numberNfe='" + numberNfe + '\'' +
                ", cfop='" + cfop + '\'' +
                ", valueNfe='" + valueNfe + '\'' +
                ", key='" + keyNfe + '\'' +
                '}';
    }
}
