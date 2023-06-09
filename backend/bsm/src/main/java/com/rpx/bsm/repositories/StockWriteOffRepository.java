package com.rpx.bsm.repositories;


import com.rpx.bsm.entities.StockWriteOff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface StockWriteOffRepository extends JpaRepository<StockWriteOff, Long> {
    @Query("SELECT s FROM StockWriteOff s WHERE s.record between :dtStart and :dtEnd ")
    Page<StockWriteOff> findByRecordBetween(Pageable pageable, LocalDate dtStart, LocalDate dtEnd);
    @Query("SELECT s FROM StockWriteOff s WHERE s.product.title like :title AND (s.record between :dtStart and :dtEnd) ")
    Page<StockWriteOff> findByTitleAndBetween(Pageable pageable, String title, LocalDate dtStart, LocalDate dtEnd);

}