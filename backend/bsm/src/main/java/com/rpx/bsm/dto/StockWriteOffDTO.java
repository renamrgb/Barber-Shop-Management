package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Nfe;
import com.rpx.bsm.entities.StockEntry;
import com.rpx.bsm.entities.StockEntryProducts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StockEntryDTO implements Serializable {


    private Long Id;
    private String supplier;
    private List<StockEntryProductsDTO> products;
    private Nfe nfe;

    public StockEntryDTO(StockEntry obj) {
        setId(obj.getId());
        setSupplier(obj.getSupplier());
        setNfe(obj.getNfe());
        products = new ArrayList<>();
        if (obj.getProducts() != null)
            for (StockEntryProducts e : obj.getProducts())
                products.add(new StockEntryProductsDTO(e));
    }
}
