package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Nfe;
import com.rpx.bsm.entities.Product;
import com.rpx.bsm.entities.StockEntry;
import com.rpx.bsm.entities.StockEntryProducts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class NfeDTO implements Serializable {
    private Long id;
    private LocalDate dateofPurchase;
    private String numberNfe;
    private String cfop;
    private String  valueNfe;
    private String keyNfe;

    public NfeDTO(Nfe obj) {
        setId(obj.getId());
        setDateofPurchase(obj.getDateofPurchase());
        setNumberNfe(obj.getNumberNfe());
        setCfop(obj.getCfop());
        setValueNfe(obj.getKeyNfe());
        setKeyNfe(obj.getKeyNfe());
    }
}
