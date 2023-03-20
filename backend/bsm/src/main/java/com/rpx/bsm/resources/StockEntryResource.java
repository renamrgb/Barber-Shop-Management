package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ExpenseDTO;
import com.rpx.bsm.dto.InstallmentDTO;
import com.rpx.bsm.dto.StockEntryDTO;
import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.entities.Installment;
import com.rpx.bsm.entities.StockEntry;
import com.rpx.bsm.entities.StockEntryProducts;
import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.records.PayOffExpenseBody;
import com.rpx.bsm.records.StockEntryRecord;
import com.rpx.bsm.services.ExpenseService;
import com.rpx.bsm.services.StockEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/stockEntry")
public class StockEntryResource {

    @Autowired
    private StockEntryService service;

    @GetMapping
    @Transactional
    public ResponseEntity<List<StockEntryDTO>> findAll() {
        List<StockEntry> list = service.findAll();
        List<StockEntryDTO> listDto = list.stream().map(x -> new StockEntryDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @PostMapping
    public ResponseEntity<StockEntryDTO> insert(@Valid @RequestBody StockEntryRecord r) {
        StockEntry obj = service.insert(r);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(new StockEntryDTO(obj));
    }



}
