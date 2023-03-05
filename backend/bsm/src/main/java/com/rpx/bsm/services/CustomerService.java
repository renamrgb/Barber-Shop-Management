package com.rpx.bsm.services;

import com.rpx.bsm.dto.CustomerDTO;
import com.rpx.bsm.entities.*;
import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.records.CustomerRecord;
import com.rpx.bsm.repositories.CustomerRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Customer> find(String name){
        List<Customer> list= null;

        if(name.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByName(name);

        return list;
    }
    public Page<Customer> findPaged(String name, Pageable pageable){
        Page<Customer> list= null;

        if(name.isEmpty())
            list = repository.findAll(pageable);
        else
            list = repository.findByUserNameContaining(name, pageable);

        return list;
    }

    public Customer insert(CustomerRecord obj) {
        return repository.save(new Customer(obj));
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
    public CustomerDTO update(Long id, CustomerRecord obj) {
        try {
            Customer entity = repository.getReferenceById(id);
            updateData(obj, entity);
            return new CustomerDTO(new Customer(obj));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Customer updateData(CustomerRecord record, Customer entity) {

        entity.getUser().setName(record.user().name());
        entity.getUser().setEmail(record.user().email());
        if (!record.user().password().equals("*******")) {
            entity.getUser().setPassword(bCryptPasswordEncoder.encode(record.user().password()));
        }
        entity.getUser().setPassword(bCryptPasswordEncoder.encode(record.user().password()));
        entity.getUser().setPhoneNumber(record.user().phoneNumber());
        entity.getUser().setTypePerson(record.user().typePerson());
        entity.getUser().setDocument(record.user().document());
        entity.getUser().setRg(record.user().rg());
        entity.getUser().setIsActive(record.user().isActive());

        entity.getUser().getAddress().setZipCode(record.user().address().zipCode());
        entity.getUser().getAddress().setPublicPlace(record.user().address().publicPlace());
        entity.getUser().getAddress().setNeighborhood(record.user().address().neighborhood());
        entity.getUser().getAddress().setComplement(record.user().address().complement());
        entity.getUser().getAddress().setCity(record.user().address().city());
        entity.getUser().getAddress().setState(record.user().address().state());

        entity.getUser().getAccessLevel().setAuthority(NivelAcessoEnum.ROLE_ADMIN);
        return entity;
    }

    public Customer findById(Long id) {
        Optional<Customer> obj = repository.findById(id);
        Customer entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return entity;
    }

}
