package com.rpx.bsm.services;

import com.rpx.bsm.entities.LoyaltyCard;
import com.rpx.bsm.entities.ParameterValue;
import com.rpx.bsm.entities.Product;
import com.rpx.bsm.repositories.LoyaltyCardRepository;
import com.rpx.bsm.repositories.ProductRepository;
import com.rpx.bsm.resources.exceptions.DefaultErrorException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class LoyaltyCardService {
    @Autowired
    private LoyaltyCardRepository repository;

    @Autowired
    private ParameterService parameterService;

    public void setQtyUsed(LoyaltyCard obj){
        ParameterValue parameterValue = parameterService.findByPameterKey("QTY_LOYALYTY_CARD");
        if(obj.getPoints() == Long.parseLong(parameterValue.getParameter_value())){
            obj.setQuantityUsed(obj.getQuantityUsed() + 1);
            obj.setPoints(0L);
        }
    }

    @Transactional
    public LoyaltyCard update(LoyaltyCard obj) {
        try {
            LoyaltyCard old = repository.getReferenceById(obj.getId());
            updateData(old, obj);
            return repository.save(old);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(obj.getId());
        }
    }

    private void updateData(LoyaltyCard old, LoyaltyCard newL) {
        old.setPoints(newL.getPoints());
        old.setQuantityUsed(newL.getQuantityUsed());
    }

}
