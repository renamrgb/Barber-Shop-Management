package com.rpx.bsm.services;

import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.entities.Installment;
import com.rpx.bsm.entities.Parameter;
import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.records.ParameterRecord;
import com.rpx.bsm.repositories.ExpenseRepository;
import com.rpx.bsm.repositories.ParameterRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import com.rpx.bsm.resources.exceptions.ValidateInstallments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParameterService {
    @Autowired
    private ParameterRepository repository;

    public List<Parameter> findAll() {
        return repository.findAll();
    }

    public Parameter findById(Long id) {
        Optional<Parameter> obj = repository.findById(id);
        Parameter entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return entity;
    }

    @Transactional
    public Parameter update(Long id, ParameterRecord obj) {
        try {
            Parameter entity = repository.getReferenceById(id);
            return repository.save(updateData(obj, entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Parameter updateData(ParameterRecord r, Parameter obj) {
        return obj;
    }

}
