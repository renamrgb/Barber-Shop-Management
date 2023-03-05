package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Professional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    @Query("SELECT p FROM Professional p"
            +" JOIN FETCH p.user u"
            +" WHERE u.name like %:name%")
    List<Professional> findByName(String name);
    Page<Professional> findByUserNameContaining(String name, Pageable pageable);


}