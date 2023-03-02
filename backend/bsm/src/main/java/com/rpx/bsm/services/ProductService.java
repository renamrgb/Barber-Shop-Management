package com.rpx.bsm.services;

import com.rpx.bsm.dto.ProductDTO;
import com.rpx.bsm.entities.*;
import com.rpx.bsm.records.ProductRecord;
import com.rpx.bsm.repositories.ProductRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Transactional
    public Page<Product> findPaged(String title, Pageable pageable){
        Page<Product> list;
        if(title.isEmpty()) {
            list = repository.findAll(pageable);
        } else
            list = repository.findByTitleContaining(title, pageable);

        return list;
    }
    public List<Product> find(String title){
        List<Product> list;

        if(title.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByTitleContaining(title);

        return list;
    }

    public Product insert(ProductRecord record) {
        return repository.save(new Product(record));
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
            updateData(entity, new Product(record));
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
        entity.setProductProcedureType(obj.getProductProcedureType());
    }
    public Product findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        return obj.get();
    }

}
