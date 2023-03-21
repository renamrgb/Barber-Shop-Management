package com.rpx.bsm.dto;

import com.rpx.bsm.entities.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StockWriteOffDTO implements Serializable {


    private Long id;
    private ProductDTO product;
    private UserDTO user;
    private LocalDate record;
    private Integer qty;
    private String reason;

    public StockWriteOffDTO(StockWriteOff obj) {
        setId(obj.getId());
        setProduct(new ProductDTO(obj.getProduct()));
        setUser(new UserDTO(obj.getUser()));
        setQty(obj.getQty());
        setRecord(obj.getRecord());
        setReason(obj.getReason());
    }
}
