package com.rpx.bsm.dto;

import com.rpx.bsm.entities.BlockedTimes;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class BlockedTimesDTO implements Serializable {
    private Long id;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public BlockedTimesDTO(BlockedTimes obj) {
        setId(obj.getId());
        setDescription(obj.getDescription());
        setEndDate(obj.getEndDate());
        setStartDate(obj.getStartDate());
    }
}
