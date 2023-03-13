package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Organization;
import com.rpx.bsm.entities.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {}
