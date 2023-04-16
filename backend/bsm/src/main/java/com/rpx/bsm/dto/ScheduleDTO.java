package com.rpx.bsm.dto;

import com.rpx.bsm.entities.*;
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ProcedureDTO procedure;
    private ProfessionalDTO professional;

    public ScheduleDTO(Schedule obj) {
        setId(obj.getId());
        setStartDate(obj.getStartDate());
        setEndDate(obj.getEndDate());
        setClient(new CustomerDTO(obj.getCustomer()));
        setProcedure(new ProcedureDTO(obj.getProcedure()));
        setProfessional(new ProfessionalDTO(obj.getProfessional()));
    }
}

