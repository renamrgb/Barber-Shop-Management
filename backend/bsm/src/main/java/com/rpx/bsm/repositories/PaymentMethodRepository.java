package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    List<PaymentMethod> findByDescriptionContaining(String description);

    Page<PaymentMethod> findByDescriptionContaining(String description, Pageable pageable);

}
