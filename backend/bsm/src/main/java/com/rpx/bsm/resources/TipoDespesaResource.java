package com.rpx.bsm.resources;

import com.rpx.bsm.entities.TipoDespesa;
import com.rpx.bsm.records.TipoDespesaRecord;
import com.rpx.bsm.repositories.TipoDespesaRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tipoDespesas")
public class TipoDespesaResource {

    @Autowired
    private TipoDespesaRepository repository;

    public List<TipoDespesa> findAll() {
        return repository.findAll();
    }

    public TipoDespesa insert(TipoDespesaRecord obj) {
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
    public TipoDespesa update(Long id, TipoDespesaRecord obj) {
        try {
            TipoDespesa entity = repository.getReferenceById(id);
            updateData(entity, converteEmEntidade(obj));
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(TipoDespesa entity, TipoDespesa obj) {
        entity.setDescricao(obj.getDescricao());
        entity.setAtivo(obj.getAtivo());
    }

    private TipoDespesa converteEmEntidade(TipoDespesaRecord record){
        TipoDespesa obj = new TipoDespesa(record.descricao(), record.ativo());
        return obj;
    }
    public TipoDespesa findById(Long id) {
        Optional<TipoDespesa> obj = repository.findById(id);
        return obj.get();
    }

}
