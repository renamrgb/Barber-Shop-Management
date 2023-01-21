package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.ProductProcedureTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductProcedureTypesRepository extends JpaRepository<ProductProcedureTypes, Long> {
    List<ProductProcedureTypes> findByDescriptionContaining(String description);
}
