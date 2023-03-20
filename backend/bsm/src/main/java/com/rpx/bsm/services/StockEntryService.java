package com.rpx.bsm.services;

import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.entities.Installment;
import com.rpx.bsm.entities.StockEntry;
import com.rpx.bsm.entities.StockEntryProducts;
import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.records.PayOffExpenseBody;
import com.rpx.bsm.records.StockEntryRecord;
import com.rpx.bsm.repositories.ExpenseRepository;
import com.rpx.bsm.repositories.StockEntryRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import com.rpx.bsm.resources.exceptions.ValidateInstallments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

}
