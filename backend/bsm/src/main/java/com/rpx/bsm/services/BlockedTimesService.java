package com.rpx.bsm.services;

import com.rpx.bsm.entities.BlockedTimes;
import com.rpx.bsm.records.BlockedTimesRecord;
import com.rpx.bsm.repositories.BlockedTimesRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.DefaultErrorException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlockedTimesService {
    @Autowired
    private BlockedTimesRepository repository;

    public BlockedTimes insert(BlockedTimesRecord record) {
        if (formValidation(record)) return repository.save(new BlockedTimes(record));
        return new BlockedTimes();
    }

    @Transactional
    public Page<BlockedTimes> find(String description, Pageable pageable) {
        Page<BlockedTimes> list = null;

        if (description.isEmpty()) list = repository.findAll(pageable);
        else list = repository.findByDescription(description, pageable);

        return list;
    }

    List<BlockedTimes> getAll() {
        return repository.findAll();
    }

    public BlockedTimes findById(Long id) {
        Optional<BlockedTimes> obj = repository.findById(id);
        return obj.get();
    }

    public List<BlockedTimes> findByDateAndProfessional(LocalDateTime date, Long professionalId) {
        List<BlockedTimes> list = repository.findByStartDateBetween(date, professionalId);
        List<BlockedTimes> timesList = new ArrayList<>();
        for (BlockedTimes e: list) {
            if(e.getStartDate().isBefore(e.getEndDate())){
                timesList.add(new BlockedTimes(
                        e.getStartDate(),
                        e.getEndDate().withHour(23).withMinute(59),
                        e.getDescription(),
                        e.getProfessional()
                ));
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

    public Boolean formValidation(BlockedTimesRecord r) {
        Boolean valid = true;
        System.out.println(r.endDate().toLocalTime().getMinute());
        System.out.println(r.startDate().toLocalTime().getMinute());
        if ((r.startDate().getMinute() != 00 || r.endDate().getMinute() != 00) && (r.startDate().getMinute() != 30 || r.endDate().getMinute() != 30))
            throw new DefaultErrorException("Não é permitido números quebrados, apenas 30 ou 00 para o campo minuto");
        if (r.startDate().isAfter(r.endDate()))
            throw new DefaultErrorException("A data inicial deve ser menor que a data final");

        return valid;
    }

}
