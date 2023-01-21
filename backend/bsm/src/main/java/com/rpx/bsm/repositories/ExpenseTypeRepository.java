package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDespesaRepository  extends JpaRepository<ExpenseType, Long> {}
