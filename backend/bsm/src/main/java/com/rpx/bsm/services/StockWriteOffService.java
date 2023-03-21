package com.rpx.bsm.services;

import com.rpx.bsm.entities.Product;
import com.rpx.bsm.entities.StockWriteOff;
import com.rpx.bsm.records.StockWriteOffRecord;
import com.rpx.bsm.repositories.StockWriteOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private void updateStock(Integer qty, Product product){
        stockService.decrementStock(product.getId(), qty);
    }
}
