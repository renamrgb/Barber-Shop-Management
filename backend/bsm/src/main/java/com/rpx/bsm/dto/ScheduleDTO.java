package com.rpx.bsm.dto;

import com.rpx.bsm.entities.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDTO implements Serializable {
    private Long id;
    private CustomerDTO client;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<ProcedureDTO> procedures;
    private ProfessionalDTO professional;
    private List<ServiceItemsDTO> products;
    private PaymentMethod paymentMethod;

    public ScheduleDTO(Schedule obj) {
        setId(obj.getId());
        setStartDate(obj.getStartDate());
        setEndDate(obj.getEndDate());
        setClient(new CustomerDTO(obj.getCustomer()));
        List<Procedure> list = new ArrayList<>(obj.getProcedures());
        List<ProcedureDTO> listaDTO = list.stream().map(modelo -> {
            ProcedureDTO dto = new ProcedureDTO(modelo);
            return dto;
        }).collect(Collectors.toList());
        setProcedures(listaDTO);
        setProfessional(new ProfessionalDTO(obj.getProfessional()));
        List<ServiceItemsDTO> serviceItemsDTOs = obj.getServiceItems().stream().map(modelo -> {
            ServiceItemsDTO serviceItemsDTO = new ServiceItemsDTO(modelo);
            return serviceItemsDTO;
        }).collect(Collectors.toList());
        setProducts(serviceItemsDTOs);
        setPaymentMethod(obj.getPaymentMethod());
    }
}

