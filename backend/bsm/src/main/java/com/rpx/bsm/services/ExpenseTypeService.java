package com.rpx.bsm.services;

import com.rpx.bsm.entities.ExpenseType;
import com.rpx.bsm.records.ExpenseTypeRecord;
import com.rpx.bsm.repositories.ExpenseTypeRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoDespesaService {

    @Autowired
    private ExpenseTypeRepository repository;

    public List<ExpenseType> findAll() {
        return repository.findAll();
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
        entity.setDescricao(obj.getDescricao());
        entity.setAtivo(obj.getAtivo());
    }

    private ExpenseType converterEmentidade(ExpenseTypeRecord record) {
        return new ExpenseType(record.descricao(), record.ativo());
    }
    public ExpenseType findById(Long id) {
        Optional<ExpenseType> obj = repository.findById(id);
        return obj.get();
    }

}
