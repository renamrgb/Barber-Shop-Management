package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Parameter;
import com.rpx.bsm.entities.ParameterValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterValueRepository extends JpaRepository<ParameterValue, Long> {}
