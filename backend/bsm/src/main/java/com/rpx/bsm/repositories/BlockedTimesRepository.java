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

    @Query(value = "SELECT b FROM BlockedTimes b WHERE b.professional.Id = :professionalId AND (:date BETWEEN b.startDate AND b.endDate)")
    List<BlockedTimes> findByStartDateBetween(LocalDateTime date, Long professionalId);

}
