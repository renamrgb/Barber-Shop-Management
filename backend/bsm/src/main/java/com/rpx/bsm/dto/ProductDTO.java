package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Product;
import com.rpx.bsm.entities.ProductProcedureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO implements Serializable {

    private Long Id;
    private String title;
    private Double price;
    private String brand;
    private Integer quantity;
    private ProductProcedureType productType;
    private Boolean isActive;

    public ProductDTO (Product obj){
        setId(obj.getId());
        setTitle(obj.getTitle());
        setPrice(obj.getPrice());
        setBrand(obj.getBrand());
        if(obj.getProductProcedureType() != null){
            setProductType(obj.getProductProcedureType());
        }else{
            setProductType(new ProductProcedureType(0L));
        }
        setQuantity(obj.getQuantity());
        setIsActive(obj.getIsActive());
    }

}
