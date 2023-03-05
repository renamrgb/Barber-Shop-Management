package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.ProductProcedureType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductProcedureTypesRepository extends JpaRepository<ProductProcedureType, Long> {
    List<ProductProcedureType> findByDescriptionContaining(String description);
    Page<ProductProcedureType> findByDescriptionContaining(String description, Pageable pageable);
}
