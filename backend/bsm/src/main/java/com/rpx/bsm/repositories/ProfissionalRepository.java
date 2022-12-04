package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Cliente;
import com.rpx.bsm.entities.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository  extends JpaRepository<Profissional, Long> {}