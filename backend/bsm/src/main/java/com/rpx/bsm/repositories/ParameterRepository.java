package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Parameter;
import com.rpx.bsm.entities.ParameterValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    @Query(value = "SELECT p FROM ParameterValue p WHERE p.parameter_key like :parameterKey")
    ParameterValue findByParameterKey(String parameterKey);
}
