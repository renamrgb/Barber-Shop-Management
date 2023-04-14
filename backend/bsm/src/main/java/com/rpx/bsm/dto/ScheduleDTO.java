package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Address;
import com.rpx.bsm.entities.Customer;
import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.entities.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDTO implements Serializable {
    private Long id;
    private CustomerDTO client;
    private LocalDateTime date;
    private ProcedureDTO procedure;

    public ScheduleDTO(Schedule obj) {
        setId(obj.getId());
        setDate(obj.getDate());
        setClient(new CustomerDTO(obj.getClient()));
        setProcedure(new ProcedureDTO(obj.getProcedure()));
    }

}

