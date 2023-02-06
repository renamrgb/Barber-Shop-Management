package com.rpx.bsm.services;

import com.rpx.bsm.dto.ProcedimentoDTO;
import com.rpx.bsm.dto.ProfissionalDTO;
import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.entities.Profissional;
import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.records.ProcedimentoIdRecord;
import com.rpx.bsm.records.ProfissionalRecord;
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
import java.util.stream.Collectors;

@Service
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository repository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<ProfissionalDTO> findAll() {
        List<Profissional> all = repository.findAll();
        return all.stream().map(this::converteEmDTO).collect(Collectors.toList());
    }

    public ProfissionalDTO insert(ProfissionalRecord obj) {
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
    public ProfissionalDTO update(Long id, ProfissionalRecord obj) {
        try {
            Profissional entity = repository.getReferenceById(id);
            updateData(obj, entity);
            return converteEmDTO(repository.save(entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Profissional converteEmEntidade(ProfissionalRecord record) {

        Profissional entidade = new Profissional();

        entidade.getUsuario().setName(record.usuario().name());
        entidade.getUsuario().setEmail(record.usuario().email());
        entidade.getUsuario().setPassword(bCryptPasswordEncoder.encode(record.usuario().password()));
        entidade.getUsuario().setPhoneNumber(record.usuario().phoneNumber());
        entidade.getUsuario().setCpf(record.usuario().cpf());
        entidade.getUsuario().setRg(record.usuario().rg());
        entidade.getUsuario().setIsActive(record.usuario().isActive());

        entidade.getUsuario().getAddres().setZipCode(record.usuario().address().zipCode());
        entidade.getUsuario().getAddres().setPublicPlace(record.usuario().address().publicPlace());
        entidade.getUsuario().getAddres().setNeighborhood(record.usuario().address().neighborhood());
        entidade.getUsuario().getAddres().setComplement(record.usuario().address().complement());
        entidade.getUsuario().getAddres().setCity(record.usuario().address().city());
        entidade.getUsuario().getAddres().setState(record.usuario().address().state());

        entidade.getUsuario().getNivelAcesso().setAuthority(NivelAcessoEnum.ROLE_ADMIN);

        for (ProcedimentoIdRecord p : record.procedimentos()) {
            entidade.getProcedimentos().add(new Procedure(p.id()));
        }

        return entidade;
    }

    private Profissional updateData(ProfissionalRecord record, Profissional entidade) {


        entidade.getUsuario().setName(record.usuario().name());
        entidade.getUsuario().setEmail(record.usuario().email());
        if (!record.usuario().password().equals("*******")) {
            entidade.getUsuario().setPassword(bCryptPasswordEncoder.encode(record.usuario().password()));
        }
        entidade.getUsuario().setPhoneNumber(record.usuario().phoneNumber());
        entidade.getUsuario().setCpf(record.usuario().cpf());
        entidade.getUsuario().setRg(record.usuario().rg());
        entidade.getUsuario().setIsActive(record.usuario().isActive());

        entidade.getUsuario().getAddres().setZipCode(record.usuario().address().zipCode());
        entidade.getUsuario().getAddres().setPublicPlace(record.usuario().address().publicPlace());
        entidade.getUsuario().getAddres().setNeighborhood(record.usuario().address().neighborhood());
        entidade.getUsuario().getAddres().setComplement(record.usuario().address().complement());
        entidade.getUsuario().getAddres().setCity(record.usuario().address().city());
        entidade.getUsuario().getAddres().setState(record.usuario().address().state());
        for (ProcedimentoIdRecord p : record.procedimentos()) {
            entidade.getProcedimentos().add(new Procedure(p.id()));
        }

        return entidade;
    }

    @Transactional
    private ProfissionalDTO converteEmDTO(Profissional profissional) {

        ProfissionalDTO dto = new ProfissionalDTO();
        dto.setId(profissional.getId());
        dto.setName(profissional.getUsuario().getName());
        dto.setEmail(profissional.getUsuario().getEmail());
        dto.setPhoneNumber(profissional.getUsuario().getPhoneNumber());
        dto.setCpf(profissional.getUsuario().getCpf());
        dto.setRg(profissional.getUsuario().getRg());
        dto.setAtivo(profissional.getUsuario().getIsActive());

        dto.getAddres().setZipCode(profissional.getUsuario().getAddres().getZipCode());
        dto.getAddres().setPublicPlace(profissional.getUsuario().getAddres().getPublicPlace());
        dto.getAddres().setNeighborhood(profissional.getUsuario().getAddres().getNeighborhood());
        dto.getAddres().setComplement(profissional.getUsuario().getAddres().getComplement());
        dto.getAddres().setCity(profissional.getUsuario().getAddres().getCity());
        dto.getAddres().setState(profissional.getUsuario().getAddres().getState());
        for (Procedure p : profissional.getProcedimentos()) {
            dto.getProcedimentos().add(new ProcedimentoDTO(p.getId()));
        }

        return dto;
    }

    public Profissional findById(Long id) {
        Optional<Profissional> obj = repository.findById(id);
        Profissional entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return entity;
    }

}
