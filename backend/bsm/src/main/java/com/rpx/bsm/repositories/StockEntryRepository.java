package com.rpx.bsm.repositories;


import com.rpx.bsm.entities.StockEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {

    Page<StockEntry> findBySupplierContaining(String supplier, Pageable pageable);
    @Query(value = "SELECT *" +
            "FROM STOCK_ENTRY S " +
            " INNER JOIN NFE N ON N.STOCK_ENTRY_ID = S.ID " +
            "WHERE " +
            "  N.DATEOF_PURCHASE BETWEEN :min AND :max", nativeQuery = true)
    Page<StockEntry> findByNfeDateofPurchaseBetween(LocalDate min, LocalDate max, Pageable pageable);
    @Query(value = "SELECT *" +
            "FROM STOCK_ENTRY S " +
            " INNER JOIN NFE N ON N.STOCK_ENTRY_ID = S.ID " +
            "WHERE " +
            "  N.DATEOF_PURCHASE BETWEEN :min AND :max AND S.SUPPLIER LIKE %:supplier%", nativeQuery = true)
    Page<StockEntry> findByNfeDateofPurchaseBetweenAAndSupplier(String supplier, LocalDate min, LocalDate max, Pageable pageable);
}
