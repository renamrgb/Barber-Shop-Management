package com.rpx.bsm.services;

import com.rpx.bsm.entities.BlockedTimes;
import com.rpx.bsm.records.BlockedTimesRecord;
import com.rpx.bsm.repositories.BlockedTimesRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlockedTimesService {
    @Autowired
    private BlockedTimesRepository repository;

    public BlockedTimes insert(BlockedTimesRecord record) {
        return repository.save(new BlockedTimes(record));
    }

    @Transactional
    public Page<BlockedTimes> find(String description, Pageable pageable) {
        Page<BlockedTimes> list = null;

        if (description.isEmpty()) list = repository.findAll(pageable);
        else list = repository.findByDescription(description, pageable);

        return list;
    }

    public BlockedTimes findById(Long id) {
        Optional<BlockedTimes> obj = repository.findById(id);
        return obj.get();
    }

    public List<BlockedTimes> findByDate(LocalDateTime date, Long professionalId){
        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);
        List<BlockedTimes> timesList = repository.findByStartDateBetween(start, end, professionalId);
        if(timesList.get(0).getStartDate().toLocalDate().equals(start.toLocalDate())){
            for(BlockedTimes b : timesList){
                b.getStartDate().withDayOfMonth(start.getDayOfMonth());
            }
        }
        return timesList;
    }

    @Transactional
    public BlockedTimes update(Long id, BlockedTimesRecord record) {
        try {
            BlockedTimes obj = findById(id);
            obj = updateData(record, obj);
            return repository.save(obj);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private BlockedTimes updateData(BlockedTimesRecord record, BlockedTimes obj) {
        obj.setEndDate(record.endDate());
        obj.setStartDate(record.startDate());
        obj.setDescription(record.description());
        return obj;
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
