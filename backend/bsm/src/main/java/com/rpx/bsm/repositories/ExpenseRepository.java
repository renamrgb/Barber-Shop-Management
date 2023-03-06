package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByDescriptionContaining(String description);

    Page<Expense> findByDescriptionContaining(String description, Pageable pageable);

}
