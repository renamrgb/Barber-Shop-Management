package com.rpx.bsm.services;

import com.rpx.bsm.entities.MessageTemplate;
import com.rpx.bsm.records.MessageTemplateRecord;
import com.rpx.bsm.repositories.MessageTemplateRepository;
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
    private MessageTemplateRepository repository;

    public List<MessageTemplate> findAll() {
        return repository.findAll();
    }

    public MessageTemplate insert(MessageTemplateRecord record) {
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
    public MessageTemplate update(Long id, MessageTemplateRecord record) {
        try {
            MessageTemplate entity = repository.getReferenceById(id);
            updateData(entity, this.converterEmEntidade(record));
            return repository.save(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(MessageTemplate entity, MessageTemplate obj) {
        entity.setTitulo(obj.getTitulo());
        entity.setMensagem(obj.getMensagem());
        entity.setAtivo(obj.getAtivo());
    }

    public MessageTemplate findById(Long id) {
        Optional<MessageTemplate> obj = repository.findById(id);
        return obj.get();
    }

    private MessageTemplate converterEmEntidade(MessageTemplateRecord record){
        return new MessageTemplate(record.titulo(), record.mensagem(), record.ativo());
    }

}
