package com.rpx.bsm.resources;

import com.rpx.bsm.entities.FormaPagamento;
import com.rpx.bsm.records.FormaDePagamentoRecord;
import com.rpx.bsm.services.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/formaPagamentos")
public class FormaPagamentoResource {

    @Autowired
    private FormaPagamentoService service;

    @GetMapping
    public ResponseEntity<List<FormaPagamento>> findAll() {
        List<FormaPagamento> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<FormaPagamento> insert(@Valid @RequestBody FormaDePagamentoRecord obj) {
        FormaPagamento fp = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fp.getId()).toUri();
        return ResponseEntity.created(uri).body(fp);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FormaPagamento> findById(@PathVariable Long id){
        FormaPagamento obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FormaPagamento> update(@PathVariable Long id, @RequestBody FormaDePagamentoRecord obj) {
        FormaPagamento entidade = service.update(id, obj);
        return ResponseEntity.ok().body(entidade);
    }

}
