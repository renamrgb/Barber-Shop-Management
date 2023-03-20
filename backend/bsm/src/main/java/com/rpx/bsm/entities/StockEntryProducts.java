package com.rpx.bsm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rpx.bsm.records.StockEntryProductRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stock_entry_products")
public class StockEntryProducts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long id;
    @ManyToOne
    private StockEntry stockEntry;
    @ManyToOne
    private Product product;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Double price;

    public StockEntryProducts(StockEntry stockEntry, Product product, Integer quantity, Double price) {
        this.stockEntry = stockEntry;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public StockEntryProducts(StockEntryProductRecord r, StockEntry stockEntry){
        setProduct(new Product(r.product().getId()));
        setQuantity(r.quantity());
        setPrice(r.price());
        setStockEntry(stockEntry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntryProducts that = (StockEntryProducts) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "StockEntryProducts{" +
                "id=" + id +
                ", stockEntry=" + stockEntry +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
