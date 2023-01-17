package com.rpx.bsm.services;

import com.rpx.bsm.entities.PaymentMethod;
import com.rpx.bsm.records.PaymentMethodRecord;
import com.rpx.bsm.repositories.paymentMethodRepository;
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
public class PaymentMethodService {

    @Autowired
    private paymentMethodRepository repository;

    public List<PaymentMethod> findAll() {
        return repository.findAll();
    }

    public List<PaymentMethod> find(String description){
        List<PaymentMethod> list;

        if(description.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByDescriptionContaining(description);

        return list;
    }

    public PaymentMethod insert(PaymentMethodRecord obj) {
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
    public PaymentMethod update(Long id, PaymentMethodRecord obj) {
        try {
            PaymentMethod entity = repository.getReferenceById(id);
            updateData(entity, converteEmEntidade(obj));
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(PaymentMethod entity, PaymentMethod obj) {
        entity.setDescription(obj.getDescription());
        entity.setIsActive(obj.getIsActive());
    }

    private PaymentMethod converteEmEntidade(PaymentMethodRecord record){
        PaymentMethod fp = new PaymentMethod(record.description(), record.isActive());
        return fp;
    }
    public PaymentMethod findById(Long id) {
        Optional<PaymentMethod> obj = repository.findById(id);
        return obj.get();
    }

    public List<PaymentMethod> findByDescription(String filtro){
        return repository.findByDescriptionContaining(filtro);
    }

}
