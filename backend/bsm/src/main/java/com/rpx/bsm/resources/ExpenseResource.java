package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ExpenseDTO;
import com.rpx.bsm.dto.InstallmentDTO;
import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.entities.Installment;
import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    @PostMapping(value = "/generateInstallments")
    public ResponseEntity<List<InstallmentDTO>> GenerateInstallments(@Valid @RequestBody ExpenseRecord r) {
        List<Installment> list = service.GenerateInstallments(r);
        List<InstallmentDTO> listDto = list.stream().map(x -> new InstallmentDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> insert(@Valid @RequestBody ExpenseRecord r) {
        Expense obj = service.insert(r);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(new ExpenseDTO(obj));
    }

}
