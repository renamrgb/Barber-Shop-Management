package com.rpx.bsm.services;

import com.rpx.bsm.entities.ProductProcedureType;
import com.rpx.bsm.records.ProductProcedureTypesRecord;
import com.rpx.bsm.repositories.ProductProcedureTypesRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductProcedureTypesService {

    @Autowired
    private ProductProcedureTypesRepository repository;

    public List<ProductProcedureType> find(String description) {
        List<ProductProcedureType> list;

        if (description.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByDescriptionContaining(description);

        return list;
    }

    public Page<ProductProcedureType> findPaged(String description, Pageable pageable) {
        Page<ProductProcedureType> list;

        if (description.isEmpty())
            list = repository.findAll(pageable);
        else
            list = repository.findByDescriptionContaining(description, pageable);

        return list;
    }

    public ProductProcedureType insert(ProductProcedureTypesRecord record) {
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
    public ProductProcedureType update(Long id, ProductProcedureTypesRecord record) {
        try {
            ProductProcedureType entity = repository.getReferenceById(id);
            updateData(entity, this.converterEmentidade(record));
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(ProductProcedureType entity, ProductProcedureType obj) {
        entity.setDescription(obj.getDescription());
        entity.setIsActive(obj.getIsActive());
    }

    private ProductProcedureType converterEmentidade(ProductProcedureTypesRecord record) {
        return new ProductProcedureType(record.description(), record.isActive());
    }

    public ProductProcedureType findById(Long id) {
        Optional<ProductProcedureType> obj = repository.findById(id);
        return obj.get();
    }

}
