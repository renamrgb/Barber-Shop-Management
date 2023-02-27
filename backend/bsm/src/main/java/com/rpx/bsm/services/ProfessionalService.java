package com.rpx.bsm.services;

import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.entities.Professional;
import com.rpx.bsm.records.ProcedimentoIdRecord;
import com.rpx.bsm.records.ProfessionalRecord;
import com.rpx.bsm.repositories.ProfessionalRepository;
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
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository repository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Professional> find(String name) {
        List<Professional> list = null;

        if (name.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByName(name);

        return list;
    }

    public Professional insert(ProfessionalRecord record) {
        return repository.save(new Professional(record));
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
    public Professional update(Long id, ProfessionalRecord obj) {
        try {
            Professional entity = repository.getReferenceById(id);
            updateData(obj, entity);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Professional updateData(ProfessionalRecord record, Professional entidade) {

        entidade.getUser().setName(record.user().name());
        entidade.getUser().setEmail(record.user().email());
 
        if (record.user().password() != null && !record.user().password().equals("*******")) {
            entidade.getUser().setPassword(bCryptPasswordEncoder.encode(record.user().password()));
        }

        entidade.getUser().setPhoneNumber(record.user().phoneNumber());
        entidade.getUser().setTypePerson(record.user().typePerson());
        entidade.getUser().setDocument(record.user().document());
        entidade.getUser().setRg(record.user().rg());
        entidade.getUser().setIsActive(record.user().isActive());

        entidade.getUser().getAddress().setZipCode(record.user().address().zipCode());
        entidade.getUser().getAddress().setPublicPlace(record.user().address().publicPlace());
        entidade.getUser().getAddress().setNeighborhood(record.user().address().neighborhood());
        entidade.getUser().getAddress().setComplement(record.user().address().complement());
        entidade.getUser().getAddress().setCity(record.user().address().city());
        entidade.getUser().getAddress().setState(record.user().address().state());
        for (ProcedimentoIdRecord p : record.procedures()) {
            entidade.getProcedures().add(new Procedure(p.id()));
        }

        return entidade;
    }

    public Professional findById(Long id) {
        Optional<Professional> obj = repository.findById(id);
        Professional entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return entity;
    }

}
