package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByDescriptionContaining(String description);
    Page<Expense> findByDescriptionContaining(String description, Pageable pageable);
    @Query(value = "SELECT * FROM EXPENSE E WHERE  E.RELEASE_DATE BETWEEN :min AND :max AND E.ID IN (SELECT I.EXPENSE_ID FROM INSTALLMENT I WHERE I.PAID = TRUE) ", nativeQuery = true)
    Page<Expense> findByReleaseDateBetweenAndPaid(LocalDate min, LocalDate max, Pageable pageable);
    @Query(value = "SELECT * FROM EXPENSE E WHERE  E.RELEASE_DATE BETWEEN :min AND :max AND E.ID IN (SELECT I.EXPENSE_ID FROM INSTALLMENT I WHERE I.PAID = TRUE) AND E.DESCRIPTION LIKE %:description%", nativeQuery = true)
    Page<Expense> findByReleaseDateBetweenAndPaidAndDescription(LocalDate min, LocalDate max, String description, Pageable pageable);
    @Query(value = "SELECT e FROM Expense e WHERE e.releaseDate between :min and :max")
    Page<Expense> findAll(LocalDate min, LocalDate max, Pageable pageable);
    @Query(value = "SELECT e FROM Expense e WHERE e.releaseDate between :min and :max AND e.description LIKE %:descriptio%")
    Page<Expense> findByDescriptionAndBetweenReleaseDate(LocalDate min, LocalDate max, String descriptio, Pageable pageable);
}