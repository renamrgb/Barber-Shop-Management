package com.rpx.bsm.services;

import com.rpx.bsm.entities.TipoProdutoProcedimento;
import com.rpx.bsm.records.TipoProdutoProcedimentoRecord;
import com.rpx.bsm.repositories.TipoProdutoProcedimentoRepository;
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
public class TipoProdutoProcedimentoService {

    @Autowired
    private TipoProdutoProcedimentoRepository repository;

    public List<TipoProdutoProcedimento> findAll() {
        return repository.findAll();
    }

    public TipoProdutoProcedimento insert(TipoProdutoProcedimentoRecord record) {
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
    public TipoProdutoProcedimento update(Long id, TipoProdutoProcedimentoRecord record) {
        try {
            TipoProdutoProcedimento entity = repository.getReferenceById(id);
            updateData(entity, this.converterEmentidade(record));
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(TipoProdutoProcedimento entity, TipoProdutoProcedimento obj) {
        entity.setDescricao(obj.getDescricao());
        entity.setAtivo(obj.getAtivo());
    }

    private TipoProdutoProcedimento converterEmentidade(TipoProdutoProcedimentoRecord record) {
        return new TipoProdutoProcedimento(record.descricao(), record.ativo());
    }
    public TipoProdutoProcedimento findById(Long id) {
        Optional<TipoProdutoProcedimento> obj = repository.findById(id);
        return obj.get();
    }

}
