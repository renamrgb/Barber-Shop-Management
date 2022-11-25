package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository  extends JpaRepository<Cliente, Long> {}
