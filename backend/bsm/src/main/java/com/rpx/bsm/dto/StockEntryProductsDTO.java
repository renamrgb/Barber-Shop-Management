package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Product;
import com.rpx.bsm.entities.StockEntryProducts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class StockEntryProductsDTO implements Serializable {

    private Long id;
    private Product product;
    private Integer quantity;
    private Double price;

    public StockEntryProductsDTO(StockEntryProducts obj) {
        setId(obj.getId());
        setProduct(obj.getProduct());
        setQuantity(obj.getQuantity());
        setPrice(obj.getPrice());
    }
}
