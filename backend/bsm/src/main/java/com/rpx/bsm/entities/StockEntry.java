package com.rpx.bsm.entities;

import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.records.StockEntryRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "stock_entry")
public class StockEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    @Column(columnDefinition = "VARCHAR(60)")
    private String supplier;
    @OneToMany(mappedBy = "stockEntry", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<StockEntryProducts> products;
    @OneToOne(mappedBy = "stockEntry", cascade = CascadeType.ALL)
    private Nfe nfe;
    private Boolean reversed;
    //@ManyToOne
    //@JoinColumn(name = "expense_id")
    //@Fetch(FetchMode.JOIN)
    //private Expense expenseStockEntry;

    public StockEntry(StockEntryRecord r) {
        setSupplier(r.supplier());
        setNfe(new Nfe(r.nfe(), this));
        this.products = new ArrayList<>();
        for(int i=0; i<r.products().size(); i++){
            this.products.add(new StockEntryProducts(r.products().get(i), this));
        }
        this.reversed = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntry that = (StockEntry) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "StockEntry{" +
                "Id=" + Id +
                ", supplier='" + supplier + '\'' +
                ", products=" + products +
                ", nfe=" + nfe +
                '}';
    }
}
