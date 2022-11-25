package com.rpx.bsm.services;

import com.rpx.bsm.entities.ModeloMensagem;
import com.rpx.bsm.entities.Produto;
import com.rpx.bsm.repositories.ModeloMensagemRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ModeloMensagemService {

    @Autowired
    private ModeloMensagemRepository repository;

    public List<ModeloMensagem> findAll() {
        return repository.findAll();
    }

    public ModeloMensagem insert(ModeloMensagem obj) {
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

    public ModeloMensagem update(Long id, ModeloMensagem obj) {
        try {
            ModeloMensagem entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(ModeloMensagem entity, ModeloMensagem obj) {
        entity.setTitulo(obj.getTitulo());
        entity.setMensagem(obj.getMensagem());
        entity.setAtivo(obj.getAtivo());
    }

}
