package com.rpx.bsm.services;

import com.rpx.bsm.entities.FormaPagamento;
import com.rpx.bsm.records.FormaDePagamentoRecord;
import com.rpx.bsm.repositories.FormaPagamentoRepository;
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
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository repository;

    public List<FormaPagamento> findAll() {
        return repository.findAll();
    }

    public FormaPagamento insert(FormaDePagamentoRecord obj) {
        return repository.save(converteEmEntidade(obj));
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
    public FormaPagamento update(Long id, FormaDePagamentoRecord obj) {
        try {
            FormaPagamento entity = repository.getReferenceById(id);
            updateData(entity, converteEmEntidade(obj));
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(FormaPagamento entity, FormaPagamento obj) {
        entity.setDescricao(obj.getDescricao());
        entity.setAtivo(obj.getAtivo());
    }

    private FormaPagamento converteEmEntidade(FormaDePagamentoRecord record){
        FormaPagamento fp = new FormaPagamento(record.descricao(), record.ativo());
        return fp;
    }
    public FormaPagamento findById(Long id) {
        Optional<FormaPagamento> obj = repository.findById(id);
        return obj.get();
    }

}
