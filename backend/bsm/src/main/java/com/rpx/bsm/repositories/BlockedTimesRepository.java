package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.BlockedTimes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BlockedTimesRepository extends JpaRepository<BlockedTimes, Long> {

    Page<BlockedTimes> findByDescription(String description, Pageable pageable);

    @Query(value = "SELECT * FROM BLOCKED_TIMES WHERE PROFESSIONAL_ID = :professionalId AND (CAST(:date AS DATE) BETWEEN CAST(START_DATE AS DATE) AND CAST(END_DATE AS DATE))", nativeQuery = true)
    List<BlockedTimes> findByStartDateBetween(LocalDateTime date, Long professionalId);

}