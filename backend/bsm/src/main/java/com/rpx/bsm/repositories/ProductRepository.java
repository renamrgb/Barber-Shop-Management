package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContaining(String title);
}
