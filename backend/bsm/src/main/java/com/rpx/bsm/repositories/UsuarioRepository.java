package com.rpx.bsm.repositories;


import com.rpx.bsm.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Page<User> findByNameContaining(String name, Pageable pageable);

}
