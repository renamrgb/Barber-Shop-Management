package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c"
            +" JOIN FETCH c.user u"
            +" WHERE u.name like %:name%")
    List<Customer> findByName(String name);
}
