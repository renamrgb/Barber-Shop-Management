package com.rpx.bsm.services;

import com.rpx.bsm.entities.Product;
import com.rpx.bsm.entities.StockWriteOff;
import com.rpx.bsm.records.StockWriteOffRecord;
import com.rpx.bsm.repositories.StockWriteOffRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class StockWriteOffService {
    @Autowired
    private StockWriteOffRepository repository;
    @Autowired
    private StockService stockService;

    public StockWriteOff insert(StockWriteOffRecord r) {
        StockWriteOff obj = new StockWriteOff(r);
        this.updateStock(r.qty(), r.product());
        return repository.save(obj);
    }

    private void updateStock(Integer qty, Product product) {
        stockService.decrementStock(product.getId(), qty);
    }

    public Page<StockWriteOff> find(Pageable pageable, String title, String dtStartString, String dtEndString) {
        Page<StockWriteOff> list = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dtStart = LocalDate.parse(dtStartString, formatter);
        LocalDate dtEnd = LocalDate.parse(dtEndString, formatter);

        if (title.isEmpty()) list = repository.findByRecordBetween(pageable, dtStart, dtEnd);
        else
            list = repository.findByTitleAndBetween(pageable, title, dtStart, dtEnd);
        return list;
    }
    @Transactional
    public void delete(Long id){
        try {
            StockWriteOff obj = repository.getReferenceById(id);
            if(obj != null){
                stockService.addStock(obj.getProduct().getId(), obj.getQty());
                repository.deleteById(id);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
