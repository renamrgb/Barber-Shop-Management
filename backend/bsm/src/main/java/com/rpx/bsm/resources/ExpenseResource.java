package com.rpx.bsm.resources;

import com.rpx.bsm.dto.CustomerDTO;
import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.records.CustomerRecord;
import com.rpx.bsm.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/expenses")
public class ExpenseResource {

    @Autowired
    private ExpenseService service;

    @GetMapping
    public ResponseEntity<List<Expense>> find(@RequestParam(defaultValue = "", name = "description") String description) {
        List<Expense> list = service.find(description);
//        List<CustomerDTO> listDto = list.stream().map(x -> new CustomerDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Expense> insert(@Valid @RequestBody CustomerRecord obj) {
        Expense cli = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cli.getId()).toUri();
        return ResponseEntity.created(uri).body(cli);
    }

}
