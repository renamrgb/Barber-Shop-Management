package com.rpx.bsm.entities;

import com.rpx.bsm.records.ProductRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    @Column(nullable = false, columnDefinition = "VARCHAR(60)")
    private String title;
    @Column(nullable = false)
    private Double price;
    @Column(columnDefinition = "VARCHAR(60)")
    private String brand;
    @Column(nullable = false)
    private Integer quantity;
    @ManyToOne
    private ProductProcedureType productProcedureType;
    @Column(nullable = false)
    private Boolean isActive;

    public Product(String title, Double price, String brand, Integer quantity, Boolean isActive) {
        this.title = title;
        this.price = price;
        this.brand = brand;
        this.quantity = quantity;
        this.isActive = isActive;
    }

    public Product(String title, Double price, String brand, Integer quantity, ProductProcedureType productProcedureType, Boolean isActive) {
        this.title = title;
        this.price = price;
        this.brand = brand;
        this.quantity = quantity;
        this.productProcedureType = productProcedureType;
        this.isActive = isActive;
    }

    public Product(ProductRecord record){
        setTitle(record.title());
        setPrice(record.price());
        setBrand(record.brand());
        setQuantity(record.quantity());
        setProductProcedureType(record.productType());
        setIsActive(record.isActive());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product produto = (Product) o;
        return Objects.equals(Id, produto.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "Id=" + Id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", quantity=" + quantity +
                ", isActive=" + isActive +
                '}';
    }
}