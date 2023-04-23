package com.rpx.bsm.services;

import com.rpx.bsm.entities.Schedule;
import com.rpx.bsm.entities.ServiceItems;
import com.rpx.bsm.records.ScheduleRecord;
import com.rpx.bsm.records.util.WorkSchedule;
import com.rpx.bsm.repositories.ScheduleRepository;
import com.rpx.bsm.repositories.ServiceItemsRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ServiceItemsService {
    @Autowired
    private ServiceItemsRepository repository;
    private List<ServiceItems> nonCommonItems(List<ServiceItems> lista1, List<ServiceItems> lista2) {
        List<ServiceItems> naoComuns = new ArrayList<>();
        for (ServiceItems item : lista1) {
            if (!lista2.contains(item)) {
                naoComuns.add(item);
            }
        }
        for (ServiceItems item : lista2) {
            if (!lista1.contains(item)) {
                naoComuns.add(item);
            }
        }
        return naoComuns;
    }
    @Transactional
    public void removenonCommonItems(List<ServiceItems> lista1, List<ServiceItems> lista2){
        List<ServiceItems> items = nonCommonItems(lista1, lista2);
        for (ServiceItems e : items){
            delete(e.getId());
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
