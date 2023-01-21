package com.rpx.bsm.resources;

import com.rpx.bsm.entities.ExpenseType;
import com.rpx.bsm.records.ExpenseTypeRecord;
import com.rpx.bsm.services.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/tipoDespesas")
public class TipoDespesaResource {

    @Autowired
    private ExpenseTypeService service;

    @GetMapping
    public ResponseEntity<List<ExpenseType>> findAll() {
        List<ExpenseType> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<ExpenseType> insert(@Valid @RequestBody ExpenseTypeRecord obj) {
        ExpenseType fp = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fp.getId()).toUri();
        return ResponseEntity.created(uri).body(fp);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpenseType> findById(@PathVariable Long id){
        ExpenseType obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ExpenseType> update(@PathVariable Long id, @RequestBody ExpenseTypeRecord obj) {
        ExpenseType entidade = service.update(id, obj);
        return ResponseEntity.ok().body(entidade);
    }

}
