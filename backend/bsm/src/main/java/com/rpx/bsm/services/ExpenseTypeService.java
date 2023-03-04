package com.rpx.bsm.services;

import com.rpx.bsm.entities.ExpenseType;
import com.rpx.bsm.records.ExpenseTypeRecord;
import com.rpx.bsm.repositories.ExpenseTypeRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseTypeService {

    @Autowired
    private ExpenseTypeRepository repository;

    public List<ExpenseType> find(String description){
        List<ExpenseType> list;

        if(description.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByDescriptionContaining(description);

        return list;
    }
    @Transactional
    public Page<ExpenseType> findPaged(String description, Pageable pageable){
        Page<ExpenseType> list;

        if(description.isEmpty())
            list = repository.findAll(pageable);
        else
            list = repository.findByDescriptionContaining(description, pageable);

        return list;
    }
    public ExpenseType insert(ExpenseTypeRecord record) {
        return repository.save(this.converterEmentidade(record));
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
    public ExpenseType update(Long id, ExpenseTypeRecord record) {
        try {
            ExpenseType entity = repository.getReferenceById(id);
            updateData(entity, this.converterEmentidade(record));
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(ExpenseType entity, ExpenseType obj) {
        entity.setDescription(obj.getDescription());
        entity.setIsActive(obj.getIsActive());
        entity.setGenerateInstallments(obj.getGenerateInstallments());
    }

    private ExpenseType converterEmentidade(ExpenseTypeRecord record) {
        return new ExpenseType(record.description(), record.generateInstallments(), record.isActive());
    }
    public ExpenseType findById(Long id) {
        Optional<ExpenseType> obj = repository.findById(id);
        return obj.get();
    }

}
