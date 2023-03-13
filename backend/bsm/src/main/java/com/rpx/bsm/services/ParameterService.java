package com.rpx.bsm.services;

import com.rpx.bsm.entities.*;
import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.records.ParameterRecord;
import com.rpx.bsm.repositories.ExpenseRepository;
import com.rpx.bsm.repositories.OrganizationRepository;
import com.rpx.bsm.repositories.ParameterRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import com.rpx.bsm.resources.exceptions.ValidateInstallments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
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
    @Autowired
    private OrganizationService organizationService;

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
            Parameter entity = findById(id);
            organizationService.update(obj.organization().getId(), obj.organization());
            return repository.save(updateData(obj, entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Parameter updateData(ParameterRecord r, Parameter obj) {
        for (int i = 0; i < r.parameterValues().size(); i++) {
            obj.getParameterValues().get(i).setParameter_key(r.parameterValues().get(i).getParameter_key());
            obj.getParameterValues().get(i).setParameter_value(r.parameterValues().get(i).getParameter_value());
            obj.getParameterValues().get(i).setParameter(obj);
        }
        obj.setOrganization(r.organization());
        return obj;
    }

}
