package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
    List<MessageTemplate> findByTitleContaining(String title);

}
