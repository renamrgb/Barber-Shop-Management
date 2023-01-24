package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    List<Procedure> findByDescriptionContaining(String description);
}
