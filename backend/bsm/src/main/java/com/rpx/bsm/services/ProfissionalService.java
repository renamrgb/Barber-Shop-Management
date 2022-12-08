package com.rpx.bsm.services;

import com.rpx.bsm.dto.ProcedimentoDTO;
import com.rpx.bsm.dto.ProfissionalDTO;
import com.rpx.bsm.entities.Procedimento;
import com.rpx.bsm.entities.Profissional;
import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.records.ProcedimentoIdRecord;
import com.rpx.bsm.records.ProfissionalRecord;
import com.rpx.bsm.repositories.ProfissionalRepository;
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
import java.util.stream.Collectors;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository repository;
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

        entidade.getUsuario().setNome(record.usuario().nome());
        entidade.getUsuario().setEmail(record.usuario().email());
        entidade.getUsuario().setSenha(bCryptPasswordEncoder.encode(record.usuario().senha()));
        entidade.getUsuario().setTelefone(record.usuario().telefone());
        entidade.getUsuario().setCpf(record.usuario().cpf());
        entidade.getUsuario().setRg(record.usuario().rg());
        entidade.getUsuario().setAtivo(record.usuario().ativo());
        entidade.getUsuario().setAdmin(true);

        entidade.getUsuario().getEndereco().setCep(record.usuario().endereco().cep());
        entidade.getUsuario().getEndereco().setLogradouro(record.usuario().endereco().logradouro());
        entidade.getUsuario().getEndereco().setBairro(record.usuario().endereco().bairro());
        entidade.getUsuario().getEndereco().setComplemento(record.usuario().endereco().complemento());
        entidade.getUsuario().getEndereco().setCidade(record.usuario().endereco().cidade());
        entidade.getUsuario().getEndereco().setUf(record.usuario().endereco().uf());

        entidade.getUsuario().getNivelAcesso().setAuthority(NivelAcessoEnum.ROLE_ADMIN);

        for (ProcedimentoIdRecord p : record.procedimentos()) {
            entidade.getProcedimentos().add(new Procedimento(p.id()));
        }

        return entidade;
    }

    private Profissional updateData(ProfissionalRecord record, Profissional entidade) {


        entidade.getUsuario().setNome(record.usuario().nome());
        entidade.getUsuario().setEmail(record.usuario().email());
        entidade.getUsuario().setSenha(bCryptPasswordEncoder.encode(record.usuario().senha()));
        entidade.getUsuario().setTelefone(record.usuario().telefone());
        entidade.getUsuario().setCpf(record.usuario().cpf());
        entidade.getUsuario().setRg(record.usuario().rg());
        entidade.getUsuario().setAtivo(record.usuario().ativo());

        entidade.getUsuario().getEndereco().setCep(record.usuario().endereco().cep());
        entidade.getUsuario().getEndereco().setLogradouro(record.usuario().endereco().logradouro());
        entidade.getUsuario().getEndereco().setBairro(record.usuario().endereco().bairro());
        entidade.getUsuario().getEndereco().setComplemento(record.usuario().endereco().complemento());
        entidade.getUsuario().getEndereco().setCidade(record.usuario().endereco().cidade());
        entidade.getUsuario().getEndereco().setUf(record.usuario().endereco().uf());
        for (ProcedimentoIdRecord p : record.procedimentos()) {
            entidade.getProcedimentos().add(new Procedimento(p.id()));
        }

        return entidade;
    }

    private ProfissionalDTO converteEmDTO(Profissional profissional) {

        ProfissionalDTO dto = new ProfissionalDTO();
        dto.setId(profissional.getId());
        dto.setNome(profissional.getUsuario().getNome());
        dto.setEmail(profissional.getUsuario().getEmail());
        dto.setTelefone(profissional.getUsuario().getTelefone());
        dto.setCpf(profissional.getUsuario().getCpf());
        dto.setRg(profissional.getUsuario().getRg());
        dto.setAtivo(profissional.getUsuario().getAtivo());

        dto.getEndereco().setCep(profissional.getUsuario().getEndereco().getCep());
        dto.getEndereco().setLogradouro(profissional.getUsuario().getEndereco().getLogradouro());
        dto.getEndereco().setBairro(profissional.getUsuario().getEndereco().getBairro());
        dto.getEndereco().setComplemento(profissional.getUsuario().getEndereco().getComplemento());
        dto.getEndereco().setCidade(profissional.getUsuario().getEndereco().getCidade());
        dto.getEndereco().setUf(profissional.getUsuario().getEndereco().getUf());
        for (Procedimento p : profissional.getProcedimentos()) {
            dto.getProcedimentos().add(new ProcedimentoDTO(p.getId()));
        }

        return dto;
    }


}
