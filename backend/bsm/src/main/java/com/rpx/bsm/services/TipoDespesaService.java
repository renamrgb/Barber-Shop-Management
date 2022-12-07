package com.rpx.bsm.services;

import com.rpx.bsm.entities.TipoDespesa;
import com.rpx.bsm.records.TipoDespesaRecord;
import com.rpx.bsm.repositories.TipoDespesaRepository;
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
    private TipoDespesaRepository repository;

    public List<TipoDespesa> findAll() {
        return repository.findAll();
    }

    public TipoDespesa insert(TipoDespesaRecord record) {
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
    public TipoDespesa update(Long id, TipoDespesaRecord record) {
        try {
            TipoDespesa entity = repository.getReferenceById(id);
            updateData(entity, this.converterEmentidade(record));
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(TipoDespesa entity, TipoDespesa obj) {
        entity.setDescricao(obj.getDescricao());
        entity.setAtivo(obj.getAtivo());
    }

    private TipoDespesa converterEmentidade(TipoDespesaRecord record) {
        return new TipoDespesa(record.descricao(), record.ativo());
    }
    public TipoDespesa findById(Long id) {
        Optional<TipoDespesa> obj = repository.findById(id);
        return obj.get();
    }

}
