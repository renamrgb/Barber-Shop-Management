package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByTitleContaining(String title, Pageable pageable);
    List<Product> findByTitleContaining(String title);
}
