package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.ExpenseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
    List<ExpenseType> findByDescriptionContaining(String description);

    Page<ExpenseType> findByDescriptionContaining(String description, Pageable pageable);

}
