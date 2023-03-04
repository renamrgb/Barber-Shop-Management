package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByTitleContaining(String title, Pageable pageable);
    List<Product> findByTitleContaining(String title);
    @Query("""
            SELECT p FROM Product p             
            WHERE p.isActive = true 
            """)
    Page<Product> findAssets(Pageable pageable);
    @Query("""
            SELECT p FROM Product p             
            WHERE p.isActive = true AND p.title like %:title%
            """)
    Page<Product> findAssetsByTitle(@Param("title") String title, Pageable pageable);

}
