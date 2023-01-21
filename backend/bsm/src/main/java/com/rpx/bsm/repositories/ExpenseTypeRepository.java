package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
    List<ExpenseType> findByDescriptionContaining(String description);
}
