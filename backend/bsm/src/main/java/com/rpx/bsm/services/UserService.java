package com.rpx.bsm.services;

import com.rpx.bsm.entities.User;
import com.rpx.bsm.repositories.UsuarioRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }
    @Transactional
    public User insert(User obj) {
        return repository.save(obj);
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

    public User update(Long id, User obj) {
        try {
            User entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setPassword(obj.getPassword());
        entity.setIsActive(obj.getIsActive());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if (user == null) {
            System.err.println("User not found: " + username);
            throw new UsernameNotFoundException("Email not found");
        }
        System.out.println("User found: " + username);
        return user;
    }
}
