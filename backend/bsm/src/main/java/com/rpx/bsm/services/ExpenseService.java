package com.rpx.bsm.services;

import com.rpx.bsm.dto.ExpenseDTO;
import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.entities.ExpenseType;
import com.rpx.bsm.entities.Installment;
import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.repositories.ExpenseRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import com.rpx.bsm.resources.exceptions.ValidateInstallments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository repository;
    @Autowired
    private ExpenseTypeService expenseTypeService;

    public List<Expense> findAll() {
        return repository.findAll();
    }

    public Expense insert(ExpenseRecord r) {
        Expense obj = null;
        ExpenseType expenseType = expenseTypeService.findById(r.expenseType().getId());

        if(expenseType.getGenerateInstallments() == false && r.installments().size() > 0){
            throw new ValidateInstallments("O tipo de despesa não permite que a despesa tenha parcelas");
        }else{
            obj = repository.save(convertToEntity(r));
        }
        return obj;
    }

    public List<Installment> GenerateInstallments(ExpenseRecord r) {
        Double installmentsValue = r.total() / r.quantityOfInstallments();

        LocalDate dtStart = r.releaseDate();

        List<Installment> installments = new ArrayList<>();
        for (Long i = 0L; i < r.quantityOfInstallments(); i++) {
            dtStart = dtStart.plusDays(r.daysBeetwenInstallments());
            installments.add(new Installment(i + 1, installmentsValue, dtStart));
        }

        return installments;
    }

    public void delete(Long id) {
        try {
            Expense obj =  findById(id);
            if(obj.getExpenseType().getGenerateInstallments() == false){
                repository.deleteById(id);
            }else{
                throw new ValidateInstallments("A despesa não pode ser excluída, pois existe parcelas pendentes");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private Expense convertToEntity(ExpenseRecord r) {
        Expense obj = new Expense();

        obj.setDescription(r.description());
        obj.setReleaseDate(r.releaseDate());
        obj.setTotal(r.total());
        obj.setDaysBeetwenInstallments(r.daysBeetwenInstallments());
        obj.setQuantityOfInstallments(r.quantityOfInstallments());
        obj.setExpenseType(r.expenseType());
        obj.setDueDate(r.dueDate());
        for (Installment e : r.installments()) {
            e.setExpense(obj);
            obj.getInstallments().add(e);
        }

        return obj;
    }

    public Expense findById(Long id) {
        Optional<Expense> obj = repository.findById(id);
        Expense entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return entity;
    }

    @Transactional
    public ExpenseDTO update(Long id, ExpenseRecord obj) {
        try {
            Expense entity = repository.getReferenceById(id);
            updateData(obj, entity);
            return new ExpenseDTO(new Expense(obj));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Expense updateData(ExpenseRecord r, Expense obj) {

        obj.setDescription(r.description());
        obj.setTotal(r.total());
        obj.setInstallments(obj.getInstallments());
        obj.setExpenseType(r.expenseType());
        obj.setDaysBeetwenInstallments(r.daysBeetwenInstallments());
        obj.setQuantityOfInstallments(r.quantityOfInstallments());
        obj.setDueDate(r.dueDate());
        obj.setReleaseDate(r.releaseDate());

        return obj;
    }

}
