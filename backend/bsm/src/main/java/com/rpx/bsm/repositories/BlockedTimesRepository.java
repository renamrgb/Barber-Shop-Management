package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.BlockedTimes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedTimesRepository extends JpaRepository<BlockedTimes, Long> {

    Page<BlockedTimes> findByDescription(String description, Pageable pageable);

}
