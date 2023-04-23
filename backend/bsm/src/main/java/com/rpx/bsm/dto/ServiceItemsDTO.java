package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.entities.Schedule;
import com.rpx.bsm.entities.ServiceItems;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ServiceItemsDTO implements Serializable {
    private Long id;
    private String title;
    private Double price;
    private Long quantity;

    public ServiceItemsDTO(ServiceItems obj) {
        setId(obj.getId());
        setQuantity(obj.getQuantity());
        setPrice(obj.getPrice());
        setTitle(obj.getTitle());
    }
}

