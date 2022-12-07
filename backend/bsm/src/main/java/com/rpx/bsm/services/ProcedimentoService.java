package com.rpx.bsm.services;

import com.rpx.bsm.dto.ProcedimentoDTO;
import com.rpx.bsm.entities.Procedimento;
import com.rpx.bsm.records.ProcedimentoRecord;
import com.rpx.bsm.repositories.ProcedimentoRepository;
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
public class ProcedimentoService {

    @Autowired
    private ProcedimentoRepository repository;

    public List<Procedimento> findAll() {
        return repository.findAll();
    }

    public ProcedimentoDTO insert(ProcedimentoRecord obj) {
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
    public ProcedimentoDTO update(Long id, ProcedimentoRecord obj) {
        try {
            Procedimento entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return converteEmDTO(repository.save(entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Procedimento entity, ProcedimentoRecord record) {
        entity.setDescricao(record.descricao());
        entity.setAtivo(record.ativo());
        entity.setValor(record.valor());
    }

    private Procedimento converteEmEntidade(ProcedimentoRecord record) {

        Procedimento entidade = new Procedimento();

        entidade.setDescricao(record.descricao());
        entidade.setValor(record.valor());
        entidade.setAtivo(record.ativo());

        return entidade;
    }

    private ProcedimentoDTO converteEmDTO(Procedimento entidade) {

        ProcedimentoDTO dto = new ProcedimentoDTO(
                entidade.getId(),
                entidade.getDescricao(),
                entidade.getValor(),
                entidade.getAtivo()
        );

        return dto;
    }
    public Procedimento findById(Long id) {
        Optional<Procedimento> obj = repository.findById(id);
        return obj.get();
    }

}