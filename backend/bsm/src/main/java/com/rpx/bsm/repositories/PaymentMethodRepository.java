package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface paymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    List<PaymentMethod> findByDescriptionContaining(String description);

}
