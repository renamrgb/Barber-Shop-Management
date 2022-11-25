package com.rpx.bsm.repositories;


import com.rpx.bsm.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> { }
