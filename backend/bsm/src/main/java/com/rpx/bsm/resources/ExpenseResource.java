package com.rpx.bsm.resources;

import com.rpx.bsm.dto.CustomerDTO;
import com.rpx.bsm.dto.ExpenseDTO;
import com.rpx.bsm.dto.InstallmentDTO;
import com.rpx.bsm.entities.Customer;
import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.entities.Installment;
import com.rpx.bsm.entities.ParameterValue;
import com.rpx.bsm.records.CustomerRecord;
import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.records.PayOffExpenseBody;
import com.rpx.bsm.resources.exceptions.ResourceExceptionHandler;
import com.rpx.bsm.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/expenses")
public class ExpenseResource {

    @Autowired
    private ExpenseService service;

    @GetMapping
    @Transactional
    public ResponseEntity<List<ExpenseDTO>> findAll() {
        List<Expense> list = service.findAll();
        List<ExpenseDTO> listDto = list.stream().map(x -> new ExpenseDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
    @GetMapping(value = "/paged")
    @Transactional
    public ResponseEntity<Page<ExpenseDTO>> findPaged(@RequestParam(name = "description", defaultValue = "") String description, Pageable pageable) {
        Page<Expense> list = service.find(description, pageable);
        Page<ExpenseDTO> listDto = list.map(x -> new ExpenseDTO(x));
        return ResponseEntity.ok().body(listDto);
    }
    @PostMapping(value = "/generateInstallments")
    public ResponseEntity<List<InstallmentDTO>> GenerateInstallments(@Valid @RequestBody ExpenseRecord r) {
        List<Installment> list = service.GenerateInstallments(r);
        List<InstallmentDTO> listDto = list.stream().map(x -> new InstallmentDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
    @Transactional
    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpenseDTO> findById(@PathVariable Long id){
        Expense obj = service.findById(id);
        return ResponseEntity.ok().body(new ExpenseDTO(obj));
    }
    @Transactional
    @PutMapping(value = "/{id}")
    public ResponseEntity<ExpenseDTO> update(@PathVariable Long id, @RequestBody ExpenseRecord obj) {
        Expense objDto = service.update(id, obj);
        return ResponseEntity.ok().body(new ExpenseDTO(objDto));
    }
    @Transactional
    @PostMapping(value = "/payOffExpense/{id}")
    public ResponseEntity<ExpenseDTO> payOffExpense(@PathVariable Long id,  @RequestParam(name = "installmentId") Integer installmentId,@RequestBody PayOffExpenseBody obj) {
        Expense objDto = service.payOffExpense(obj, id,  installmentId);
        return ResponseEntity.ok().body(new ExpenseDTO(objDto));
    }
    @PostMapping
    public ResponseEntity<ExpenseDTO> insert(@Valid @RequestBody ExpenseRecord r) {
        Expense obj = service.insert(r);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(new ExpenseDTO(obj));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "/reverse")
    public ResponseEntity<ExpenseDTO> reverse(@RequestParam(name="expenseId") Long expenseId, @RequestParam(name = "installmentId") Integer installmentId) {
        Expense obj = service.reverse(expenseId, installmentId);
        return ResponseEntity.ok().body(new ExpenseDTO(obj));
    }
    @GetMapping(value = "/findByReleaseDate")
    public ResponseEntity<List<ExpenseDTO>> findByReleaseDate(@RequestParam(name = "dtStart", defaultValue = "") String dtStart, @RequestParam(name = "dtEnd", defaultValue = "") String dtEnd){
        List<Expense> list = service.findByDate(dtStart, dtEnd);
        List<ExpenseDTO> listDto = list.stream().map(x -> new ExpenseDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

}
