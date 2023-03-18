package com.rpx.bsm.services;

import com.rpx.bsm.dto.ExpenseDTO;
import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.entities.Installment;
import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.records.PayOffExpenseBody;
import com.rpx.bsm.repositories.ExpenseRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import com.rpx.bsm.resources.exceptions.ValidateInstallments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository repository;
    @Autowired
    private ExpenseTypeService expenseTypeService;

    public List<Expense> findAll() {
        return repository.findAll();
    }

    public Page<Expense> find(String description, Pageable pageable) {
        Page<Expense> list = null;

        if (description.isEmpty()) list = repository.findAll(pageable);
        else list = repository.findByDescriptionContaining(description, pageable);

        return list;
    }

    public Expense insert(ExpenseRecord r) {
        return repository.save(convertToEntity(r));
    }

    public List<Installment> GenerateInstallments(ExpenseRecord r) {

        BigDecimal dividendo = BigDecimal.valueOf(r.total());
        BigDecimal divisor = BigDecimal.valueOf(r.quantityOfInstallments());
        BigDecimal installmentsValue = dividendo.divide(divisor, 2, RoundingMode.HALF_UP);

        LocalDate dtStart = r.releaseDate();
        List<Installment> installments = new ArrayList<>();

        for (Long i = 0L; i < r.quantityOfInstallments(); i++) {
            dtStart = dtStart.plusDays(r.daysBeetwenInstallments());
            installments.add(new Installment(i + 1, installmentsValue, dtStart));
        }
        BigDecimal amount = installmentsValue.multiply(BigDecimal.valueOf(r.quantityOfInstallments()));
        if (amount != BigDecimal.valueOf(r.total())) {
            BigDecimal difference = amount.subtract(BigDecimal.valueOf(r.total()));
            if (amount.compareTo(BigDecimal.valueOf(r.total())) > 0) {
                installments.get(0).setInstallmentValue(installments.get(0).getInstallmentValue().subtract(difference));
            } else {
                installments.get(0).setInstallmentValue(installments.get(0).getInstallmentValue().add(difference.abs()));
            }
        }
        return installments;
    }
    @Transactional
    public void delete(Long id) {
        try {
            Boolean isPaid = false;
            Expense obj = findById(id);
            for (Installment e : obj.getInstallments()){
                if(e.getPaymentDate() != null){
                    isPaid = true;
                }
            }
            if (!isPaid) {
                repository.deleteById(id);
            } else {
                throw new ValidateInstallments("Despesas com parcelas pagas não podem ser excluídas!");
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
        for (Installment e : r.installments()) {
            e.setExpense(obj);
            obj.getInstallments().add(e);
        }

        return obj;
    }

    @Transactional
    public Expense findById(Long id) {
        Optional<Expense> obj = repository.findById(id);
        Expense entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return entity;
    }

    @Transactional
    public Expense update(Long id, ExpenseRecord obj) {
        try {
            Expense entity = repository.getReferenceById(id);
            return repository.save(updateData(obj, entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Transactional
    public Expense payOffExpense(ExpenseRecord record, Long id) {
        LocalDate dataAtual = LocalDate.now();
        Expense expense = findById(id);
        List<Installment> installments = record.installments();
        List<Installment> installmentAt = expense.getInstallments();

        for (int i = 0; i < installments.size(); i++) {
            installmentAt.get(i).setAmountPaid(installments.get(i).getAmountPaid());
            installmentAt.get(i).setPaymentMethod(installments.get(i).getPaymentMethod());
            installmentAt.get(i).setPaymentDate(dataAtual);
            installmentAt.get(i).setExpense(expense);
        }
        expense.setInstallments(installmentAt);
        return repository.save(expense);
    }

    @Transactional
    public Expense payOffExpense(PayOffExpenseBody record, Long expenseId, int installmentId) {
        try {
            Expense obj = repository.getReferenceById(expenseId);
            obj.getInstallments().get(installmentId).setPaymentDate(record.paymentDate());
            obj.getInstallments().get(installmentId).setAmountPaid(record.amountPaid());
            obj.getInstallments().get(installmentId).setPaymentMethod(record.paymentMethod());
            obj.getInstallments().get(installmentId).setPaid(true);
            return repository.save(obj);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(expenseId);
        }
    }

    private Expense updateData(ExpenseRecord r, Expense obj) {

        obj.setDescription(r.description());
        obj.setTotal(r.total());
        obj.setExpenseType(r.expenseType());
        obj.setDaysBeetwenInstallments(r.daysBeetwenInstallments());
        obj.setQuantityOfInstallments(r.quantityOfInstallments());
        obj.setReleaseDate(r.releaseDate());

        for (int i = 0; i < obj.getInstallments().size(); i++) {
            obj.getInstallments().get(i).setDueDate(r.installments().get(i).getDueDate());
            obj.getInstallments().get(i).setInstallmentValue(r.installments().get(i).getInstallmentValue());
        }

        return obj;
    }

    @Transactional
    public Expense reverse(Long expenseId, int installmentId) {
        try {
            Expense obj = repository.getReferenceById(expenseId);
            obj.getInstallments().get(installmentId).setPaymentDate(null);
            obj.getInstallments().get(installmentId).setAmountPaid(0.00);
            obj.getInstallments().get(installmentId).setPaymentMethod(null);
            obj.getInstallments().get(installmentId).setPaid(false);
            return repository.save(obj);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(expenseId);
        }
    }

}
