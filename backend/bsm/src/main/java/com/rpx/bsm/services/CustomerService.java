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

    public CustomerDTO insert(CustomerRecord obj) {
        return new CustomerDTO(repository.save(converteEmEntidade(obj)));
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
            return new CustomerDTO(converteEmEntidade(obj));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Customer converteEmEntidade(CustomerRecord record) {

        Customer entity = new Customer();

        entity.getUser().setName(record.usuario().name());
        entity.getUser().setEmail(record.usuario().email());
        entity.getUser().setPassword(bCryptPasswordEncoder.encode(record.usuario().password()));
        entity.getUser().setPhoneNumber(record.usuario().phoneNumber());
        entity.getUser().setCpf(record.usuario().cpf());
        entity.getUser().setRg(record.usuario().rg());
        entity.getUser().setIsActive(record.usuario().isActive());

        entity.getUser().getAddres().setZipCode(record.usuario().addres().zipCode());
        entity.getUser().getAddres().setPublicPlace(record.usuario().addres().publicPlace());
        entity.getUser().getAddres().setNeighborhood(record.usuario().addres().neighborhood());
        entity.getUser().getAddres().setComplement(record.usuario().addres().complement());
        entity.getUser().getAddres().setCity(record.usuario().addres().city());
        entity.getUser().getAddres().setState(record.usuario().addres().state());

        entity.getUser().getNivelAcesso().setAuthority(NivelAcessoEnum.ROLE_ADMIN);

        return entity;
    }

    private Customer updateData(CustomerRecord record, Customer entidade) {


        entidade = new Customer(
                new User(
                        record.usuario().name(),
                        record.usuario().email(),
                        bCryptPasswordEncoder.encode(record.usuario().password()),
                        record.usuario().phoneNumber(),
                        record.usuario().cpf(),
                        record.usuario().rg(),
                        new Addres(
                                record.usuario().addres().zipCode(),
                                record.usuario().addres().publicPlace(),
                                record.usuario().addres().neighborhood(),
                                record.usuario().addres().complement(),
                                record.usuario().addres().city(),
                                record.usuario().addres().state()
                        ),
                        new NivelAcesso(NivelAcessoEnum.ROLE_ADMIN),
                        record.usuario().isActive()
                )
        );
        return entidade;
    }

    public Customer findById(Long id) {
        Optional<Customer> obj = repository.findById(id);
        Customer entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return entity;
    }

}
