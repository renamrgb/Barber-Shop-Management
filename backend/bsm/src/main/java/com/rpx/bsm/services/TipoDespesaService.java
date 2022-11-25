package com.rpx.bsm.services;

import com.rpx.bsm.entities.Produto;
import com.rpx.bsm.entities.TipoDespesa;
import com.rpx.bsm.repositories.ProdutoRepository;
import com.rpx.bsm.repositories.TipoDespesaRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TipoDespesaService {

    @Autowired
    private TipoDespesaRepository repository;

    public List<TipoDespesa> findAll() {
        return repository.findAll();
    }

    public TipoDespesa insert(TipoDespesa obj) {
        return repository.save(obj);
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

    public TipoDespesa update(Long id, TipoDespesa obj) {
        try {
            TipoDespesa entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(TipoDespesa entity, TipoDespesa obj) {
        entity.setDescricao(obj.getDescricao());
        entity.setAtivo(obj.getAtivo());
    }

}
