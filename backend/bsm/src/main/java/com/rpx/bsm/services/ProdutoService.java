package com.rpx.bsm.services;

import com.rpx.bsm.entities.*;
import com.rpx.bsm.records.ProdutoRecord;
import com.rpx.bsm.repositories.ProdutoRepository;
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
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public Produto insert(ProdutoRecord record) {
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
    public Produto update(Long id, ProdutoRecord record) {
        try {
            Produto entity = repository.getReferenceById(id);
            updateData(entity, this.converterEmentidade(record));
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Produto entity, Produto obj) {
        entity.setTitulo(obj.getTitulo());
        entity.setValor(obj.getValor());
        entity.setQuantidade(obj.getQuantidade());
    }

    private Produto converterEmentidade(ProdutoRecord record) {
        return new Produto(record.titulo(), record.valor(), record.marca(), record.quantidade(), record.ativo());
    }
    public Produto findById(Long id) {
        Optional<Produto> obj = repository.findById(id);
        return obj.get();
    }

}
