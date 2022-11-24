package com.rpx.bsm.resources;

import com.rpx.bsm.entities.FormaPagamento;
import com.rpx.bsm.services.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<FormaPagamento> insert(@RequestBody FormaPagamento obj) {
        obj = service.insert(obj);
        return ResponseEntity.ok().body(obj);
    }



}
