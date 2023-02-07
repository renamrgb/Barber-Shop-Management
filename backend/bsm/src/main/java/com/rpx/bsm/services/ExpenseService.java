package com.rpx.bsm.services;

import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository repository;

    public List<Expense> find(String description) {
        List<Expense> list = null;

        if (description.isEmpty())
            list = repository.findAll();
        else list = repository.findByDescriptionContaining(description);
        return list;
    }

    public Expense insert(ExpenseRecord r) {
        return repository.save(convertToEntity(r));
    }

    private Expense convertToEntity(ExpenseRecord r){
        Expense obj = new Expense();

        obj.setDescription(r.description());
        obj.setReleaseDate(r.releaseDate());
        obj.setTotal(r.total());
        obj.setDaysBeetwenInstallments(r.daysBeetwenInstallments());
        obj.setQuantityOfInstallments(r.quantityOfInstallments());
        obj.setExpenseType(r.expenseType());

        return obj;
    }


}
