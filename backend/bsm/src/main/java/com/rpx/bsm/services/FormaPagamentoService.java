package com.rpx.bsm.services;

import com.rpx.bsm.entities.FormaPagamento;
import com.rpx.bsm.entities.Produto;
import com.rpx.bsm.repositories.FormaPagamentoRepository;
import com.rpx.bsm.repositories.ProdutoRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository repository;

    public List<FormaPagamento> findAll() {
        return repository.findAll();
    }

    public FormaPagamento insert(FormaPagamento obj) {
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

}
