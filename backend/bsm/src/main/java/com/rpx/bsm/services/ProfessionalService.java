package com.rpx.bsm.services;

import com.rpx.bsm.dto.ProfissionalDTO;
import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.entities.Professional;
import com.rpx.bsm.enums.NivelAcessoEnum;
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

    public List<Professional> find(String name){
        List<Professional> list= null;

        if(name.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByName(name);

        return list;
    }

    public ProfissionalDTO insert(ProfessionalRecord obj) {
        return new ProfissionalDTO(repository.save(converteEmEntidade(obj)));
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
    public ProfissionalDTO update(Long id, ProfessionalRecord obj) {
        try {
            Professional entity = repository.getReferenceById(id);
            updateData(obj, entity);
            return new ProfissionalDTO(repository.save(entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Professional converteEmEntidade(ProfessionalRecord record) {

        Professional entidade = new Professional();

        entidade.getUser().setName(record.user().name());
        entidade.getUser().setEmail(record.user().email());
        entidade.getUser().setPassword(bCryptPasswordEncoder.encode(record.user().password()));
        entidade.getUser().setPhoneNumber(record.user().phoneNumber());
        entidade.getUser().setCpf(record.user().cpf());
        entidade.getUser().setRg(record.user().rg());
        entidade.getUser().setIsActive(record.user().isActive());

        entidade.getUser().getAddress().setZipCode(record.user().address().zipCode());
        entidade.getUser().getAddress().setPublicPlace(record.user().address().publicPlace());
        entidade.getUser().getAddress().setNeighborhood(record.user().address().neighborhood());
        entidade.getUser().getAddress().setComplement(record.user().address().complement());
        entidade.getUser().getAddress().setCity(record.user().address().city());
        entidade.getUser().getAddress().setState(record.user().address().state());

        entidade.getUser().getNivelAcesso().setAuthority(NivelAcessoEnum.ROLE_ADMIN);

        for (ProcedimentoIdRecord p : record.procedures()) {
            entidade.getProcedures().add(new Procedure(p.id()));
        }

        return entidade;
    }

    private Professional updateData(ProfessionalRecord record, Professional entidade) {

        entidade.getUser().setName(record.user().name());
        entidade.getUser().setEmail(record.user().email());
        if (!record.user().password().equals("*******")) {
            entidade.getUser().setPassword(bCryptPasswordEncoder.encode(record.user().password()));
        }
        entidade.getUser().setPhoneNumber(record.user().phoneNumber());
        entidade.getUser().setCpf(record.user().cpf());
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
