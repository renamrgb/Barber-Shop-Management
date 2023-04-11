package com.rpx.bsm.services;

import com.rpx.bsm.entities.*;
import com.rpx.bsm.records.StockEntryRecord;
import com.rpx.bsm.repositories.StockEntryRepository;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class StockEntryService {
    @Autowired
    private StockEntryRepository repository;
    @Autowired
    private StockService stockService;

    public List<StockEntry> findAll() {
        return repository.findAll();
    }

    public StockEntry insert(StockEntryRecord r) {
        StockEntry obj = new StockEntry(r);
        this.updateStock(obj.getProducts());
        return repository.save(obj);
    }

    private void updateStock(List<StockEntryProducts> products) {
        for (StockEntryProducts e : products) {
            stockService.addStock(e.getProduct().getId(), e.getQuantity());
        }
    }
    public Page<StockEntry> findPaged(String dtStartString, String dtEndString, String supplier, Pageable pageable) {
        Page<StockEntry> list = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dtStart = LocalDate.parse(dtStartString, formatter);
        LocalDate dtEnd = LocalDate.parse(dtEndString, formatter);

        if(!supplier.isEmpty())
            list = repository.findByNfeDateofPurchaseBetweenAAndSupplier(supplier, dtStart, dtEnd, pageable);
        else
            list = repository.findByNfeDateofPurchaseBetween(dtStart, dtEnd, pageable);

        return list;
    }
    public StockEntry findById(Long id) {
        Optional<StockEntry> obj = repository.findById(id);
        StockEntry entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return entity;
    }
    @Transactional
    public void reverse(Long id){
        try {
            StockEntry entity = repository.getReferenceById(id);
            List<StockEntryProducts> products = entity.getProducts();
            for(StockEntryProducts p : products){
                stockService.decrementStock(p.getProduct().getId(), p.getQuantity());
            }
            entity.setReversed(true);
            repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

}
