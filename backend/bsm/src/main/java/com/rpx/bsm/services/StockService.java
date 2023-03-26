package com.rpx.bsm.services;

import com.rpx.bsm.entities.Product;
import com.rpx.bsm.entities.StockEntry;
import com.rpx.bsm.records.ProductRecord;
import com.rpx.bsm.records.StockEntryRecord;
import com.rpx.bsm.repositories.ProductRepository;
import com.rpx.bsm.repositories.StockEntryRepository;
import com.rpx.bsm.resources.exceptions.DefaultErrorException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class StockService {
    @Autowired
    private ProductRepository repository;
    @Transactional
    public Product addStock(Long id, Integer qty) {
        try {
            Product entity = repository.getReferenceById(id);
            updateData(entity, entity.getQuantity() + qty);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
    @Transactional
    public Product decrementStock(Long id, Integer qty) {
        try {
            Product entity = repository.getReferenceById(id);
            if(qty <= entity.getQuantity()){
                updateData(entity, entity.getQuantity() - qty);
                return repository.save(entity);
            }else{
                throw new DefaultErrorException("A quantidade não pode ser maior que a quantidade em estoque");
            }
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
    private void updateData(Product entity, Integer qty) {
        entity.setQuantity(qty);
    }

}
