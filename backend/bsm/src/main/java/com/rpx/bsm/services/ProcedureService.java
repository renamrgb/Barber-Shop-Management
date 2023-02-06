package com.rpx.bsm.services;

import com.rpx.bsm.dto.ProcedureDTO;
import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.records.ProcedureRecord;
import com.rpx.bsm.repositories.ProcedureRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository repository;

    public List<Procedure> find(String description){
        List<Procedure> list;

        if(description.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByDescriptionContaining(description);

        return list;
    }

    public ProcedureDTO insert(ProcedureRecord obj) {
        return converteEmDTO(repository.save(converteEmEntidade(obj)));
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
    public ProcedureDTO update(Long id, ProcedureRecord obj) {
        try {
            Procedure entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return converteEmDTO(repository.save(entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Procedure entity, ProcedureRecord record) {
        entity.setDescription(record.description());
        entity.setIsActive(record.isActive());
        entity.setPrice(record.price());
    }

    private Procedure converteEmEntidade(ProcedureRecord record) {

        Procedure entidade = new Procedure();

        entidade.setDescription(record.description());
        entidade.setPrice(record.price());
        entidade.setIsActive(record.isActive());

        return entidade;
    }

    private ProcedureDTO converteEmDTO(Procedure entidade) {

        ProcedureDTO dto = new ProcedureDTO(
                entidade.getId(),
                entidade.getDescription(),
                entidade.getPrice(),
                entidade.getIsActive()
        );

        return dto;
    }
    public Procedure findById(Long id) {
        Optional<Procedure> obj = repository.findById(id);
        return obj.get();
    }

}