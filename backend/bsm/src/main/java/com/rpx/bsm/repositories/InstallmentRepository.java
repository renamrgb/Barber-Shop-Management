package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Installment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
}
