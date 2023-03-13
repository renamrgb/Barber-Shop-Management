package com.rpx.bsm.services;

import com.rpx.bsm.entities.Organization;
import com.rpx.bsm.entities.Parameter;
import com.rpx.bsm.records.ParameterRecord;
import com.rpx.bsm.repositories.OrganizationRepository;
import com.rpx.bsm.repositories.ParameterRepository;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository repository;

    public Organization findById(Long id) {
        Optional<Organization> obj = repository.findById(id);
        Organization entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return entity;
    }

    @Transactional
    public Organization update(Long id, Organization obj) {
        try {
            Organization entity = findById(id);
            return repository.save(updateData(obj, entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Organization updateData(Organization newObj, Organization oldObj) {

        oldObj.setName(newObj.getName());
        oldObj.setCnpj(newObj.getCnpj());
        oldObj.setAddress(newObj.getAddress());

        return oldObj;
    }

}
