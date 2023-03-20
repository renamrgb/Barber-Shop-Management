package com.rpx.bsm.repositories;


import com.rpx.bsm.entities.StockEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockEntryRepository extends JpaRepository<StockEntry, Long> { }
