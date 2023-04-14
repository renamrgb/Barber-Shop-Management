package com.rpx.bsm.services;

import com.rpx.bsm.dto.ScheduleDTO;
import com.rpx.bsm.entities.Product;
import com.rpx.bsm.entities.Schedule;
import com.rpx.bsm.records.ScheduleRecord;
import com.rpx.bsm.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository repository;
    public Schedule insert(ScheduleRecord record) {
        return repository.save(new Schedule(record));
    }

    public Page<Schedule> find(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Schedule findById(Long id) {
        Optional<Schedule> obj = repository.findById(id);
        return obj.get();
    }

}
