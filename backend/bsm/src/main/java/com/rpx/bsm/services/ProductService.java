package com.rpx.bsm.services;

import com.rpx.bsm.entities.*;
import com.rpx.bsm.records.ProductRecord;
import com.rpx.bsm.repositories.ProductRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> find(String title){
        List<Product> list;

        if(title.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByTitleContaining(title);

        return list;
    }

    public Product insert(ProductRecord record) {
        return repository.save(this.converterEmentidade(record));
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
    @Transactional
    public Product update(Long id, ProductRecord record) {
        try {
            Product entity = repository.getReferenceById(id);
            updateData(entity, this.converterEmentidade(record));
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Product entity, Product obj) {
        entity.setTitle(obj.getTitle());
        entity.setPrice(obj.getPrice());
        entity.setBrand(obj.getBrand());
        entity.setQuantity(obj.getQuantity());
        entity.setIsActive(obj.getIsActive());
    }

    private Product converterEmentidade(ProductRecord record) {
        return new Product(record.title(), record.price(), record.brand(), record.quantity(), record.isActive());
    }
    public Product findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        return obj.get();
    }

}
