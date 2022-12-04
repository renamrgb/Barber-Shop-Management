package com.rpx.bsm.services;

import com.rpx.bsm.dto.ClienteDTO;
import com.rpx.bsm.records.ClienteRecord;
import com.rpx.bsm.entities.Cliente;
import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.repositories.ClienteRepository;
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
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<ClienteDTO> findAll() {
        List<Cliente> all = repository.findAll();
        return all.stream().map(this::converteEmDTO).collect(Collectors.toList());
    }

    public ClienteDTO insert(ClienteRecord obj) {
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
    public ClienteDTO update(Long id, ClienteRecord obj) {
        try {
            Cliente entity = repository.getReferenceById(id);
            updateData(obj, entity);
            return converteEmDTO(repository.save(entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Cliente converteEmEntidade(ClienteRecord record) {

        Cliente cli = new Cliente();

        cli.getUsuario().setNome(record.usuario().nome());
        cli.getUsuario().setEmail(record.usuario().email());
        cli.getUsuario().setSenha(bCryptPasswordEncoder.encode(record.usuario().senha()));
        cli.getUsuario().setTelefone(record.usuario().telefone());
        cli.getUsuario().setCpf(record.usuario().cpf());
        cli.getUsuario().setRg(record.usuario().rg());
        cli.getUsuario().setAtivo(true);
        cli.getUsuario().setAdmin(true);

        cli.getUsuario().getEndereco().setCep(record.usuario().endereco().cep());
        cli.getUsuario().getEndereco().setLogradouro(record.usuario().endereco().logradouro());
        cli.getUsuario().getEndereco().setBairro(record.usuario().endereco().bairro());
        cli.getUsuario().getEndereco().setComplemento(record.usuario().endereco().complemento());
        cli.getUsuario().getEndereco().setCidade(record.usuario().endereco().cidade());
        cli.getUsuario().getEndereco().setUf(record.usuario().endereco().uf());

        cli.getUsuario().getNivelAcesso().setAuthority(NivelAcessoEnum.ROLE_ADMIN);

        return cli;
    }

    private Cliente updateData(ClienteRecord record, Cliente entidade) {


        entidade.getUsuario().setNome(record.usuario().nome());
        entidade.getUsuario().setEmail(record.usuario().email());
        entidade.getUsuario().setSenha(bCryptPasswordEncoder.encode(record.usuario().senha()));
        entidade.getUsuario().setTelefone(record.usuario().telefone());
        entidade.getUsuario().setCpf(record.usuario().cpf());
        entidade.getUsuario().setRg(record.usuario().rg());
        entidade.getUsuario().setAtivo(record.usuario().ativo());
//        entidade.getUsuario().setAdmin(true);

        entidade.getUsuario().getEndereco().setCep(record.usuario().endereco().cep());
        entidade.getUsuario().getEndereco().setLogradouro(record.usuario().endereco().logradouro());
        entidade.getUsuario().getEndereco().setBairro(record.usuario().endereco().bairro());
        entidade.getUsuario().getEndereco().setComplemento(record.usuario().endereco().complemento());
        entidade.getUsuario().getEndereco().setCidade(record.usuario().endereco().cidade());
        entidade.getUsuario().getEndereco().setUf(record.usuario().endereco().uf());

        return entidade;
    }

    private ClienteDTO converteEmDTO(Cliente cliente) {

        ClienteDTO cli = new ClienteDTO();
        cli.setId(cliente.getId());
        cli.setNome(cliente.getUsuario().getNome());
        cli.setEmail(cliente.getUsuario().getEmail());
        cli.setTelefone(cliente.getUsuario().getTelefone());
        cli.setCpf(cliente.getUsuario().getCpf());
        cli.setRg(cliente.getUsuario().getRg());
        cli.setAtivo(cliente.getUsuario().getAtivo());

        cli.getEndereco().setCep(cliente.getUsuario().getEndereco().getCep());
        cli.getEndereco().setLogradouro(cliente.getUsuario().getEndereco().getLogradouro());
        cli.getEndereco().setBairro(cliente.getUsuario().getEndereco().getBairro());
        cli.getEndereco().setComplemento(cliente.getUsuario().getEndereco().getComplemento());
        cli.getEndereco().setCidade(cliente.getUsuario().getEndereco().getCidade());
        cli.getEndereco().setUf(cliente.getUsuario().getEndereco().getUf());

        return cli;
    }

}
