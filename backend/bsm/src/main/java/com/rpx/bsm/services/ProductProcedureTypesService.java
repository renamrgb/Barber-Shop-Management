package com.rpx.bsm.services;

import com.rpx.bsm.entities.ProductProcedureTypes;
import com.rpx.bsm.records.ProductProcedureTypesRecord;
import com.rpx.bsm.repositories.ProductProcedureTypesRepository;
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
public class ProductProcedureTypesService {

    @Autowired
    private ProductProcedureTypesRepository repository;

    public List<ProductProcedureTypes> find(String description) {
        List<ProductProcedureTypes> list;

        if (description.isEmpty())
            list = repository.findAll();
        else
            list = repository.findByDescriptionContaining(description);

        return list;
    }

    public ProductProcedureTypes insert(ProductProcedureTypesRecord record) {
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
    public ProductProcedureTypes update(Long id, ProductProcedureTypesRecord record) {
        try {
            ProductProcedureTypes entity = repository.getReferenceById(id);
            updateData(entity, this.converterEmentidade(record));
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(ProductProcedureTypes entity, ProductProcedureTypes obj) {
        entity.setDescription(obj.getDescription());
        entity.setIsActive(obj.getIsActive());
    }

    private ProductProcedureTypes converterEmentidade(ProductProcedureTypesRecord record) {
        return new ProductProcedureTypes(record.description(), record.isActive());
    }

    public ProductProcedureTypes findById(Long id) {
        Optional<ProductProcedureTypes> obj = repository.findById(id);
        return obj.get();
    }

}
