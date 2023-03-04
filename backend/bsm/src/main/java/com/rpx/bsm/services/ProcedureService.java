package com.rpx.bsm.services;

import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.records.ProcedureRecord;
import com.rpx.bsm.repositories.ProcedureRepository;
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
import java.util.List;
import java.util.Optional;

@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository repository;

    public List<Procedure> find(String description){
        List<Procedure> list;

        if(description.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByDescriptionContaining(description);

        return list;
    }

    public Page<Procedure> findPaged(String description, Pageable pageable){
        Page<Procedure> list;

        if(description.isEmpty())
            list = repository.findAll(pageable);
        else
            list = repository.findByDescriptionContaining(description, pageable);

        return list;
    }

    public Procedure insert(ProcedureRecord record) {
        return repository.save(new Procedure(record));
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

    @Transactional
    public Procedure update(Long id, ProcedureRecord obj) {
        try {
            Procedure entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Procedure entity, ProcedureRecord record) {
        entity.setDescription(record.description());
        entity.setIsActive(record.isActive());
        entity.setPrice(record.price());
        entity.setProductProcedureType(record.procedureType());
    }
    public Procedure findById(Long id) {
        Optional<Procedure> obj = repository.findById(id);
        return obj.get();
    }

}