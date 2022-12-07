package com.rpx.bsm.services;

import com.rpx.bsm.entities.ModeloMensagem;
import com.rpx.bsm.records.ModeloMensagemRecord;
import com.rpx.bsm.repositories.ModeloMensagemRepository;
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
public class ModeloMensagemService {

    @Autowired
    private ModeloMensagemRepository repository;

    public List<ModeloMensagem> findAll() {
        return repository.findAll();
    }

    public ModeloMensagem insert(ModeloMensagemRecord record) {
        return repository.save(this.converterEmEntidade(record));
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
    public ModeloMensagem update(Long id, ModeloMensagemRecord record) {
        try {
            ModeloMensagem entity = repository.getReferenceById(id);
            updateData(entity, this.converterEmEntidade(record));
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

    public ModeloMensagem findById(Long id) {
        Optional<ModeloMensagem> obj = repository.findById(id);
        return obj.get();
    }

    private ModeloMensagem converterEmEntidade(ModeloMensagemRecord record){
        return new ModeloMensagem(record.titulo(), record.mensagem(), record.ativo());
    }

}
