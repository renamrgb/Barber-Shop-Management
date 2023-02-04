package com.rpx.bsm.repositories;


import com.rpx.bsm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<User, Long> {
    User findByEmail(String name);
}
