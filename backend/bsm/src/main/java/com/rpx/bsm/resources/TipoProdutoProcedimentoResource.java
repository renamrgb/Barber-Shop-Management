package com.rpx.bsm.resources;

import com.rpx.bsm.entities.TipoProdutoProcedimento;
import com.rpx.bsm.records.TipoProdutoProcedimentoRecord;
import com.rpx.bsm.services.TipoProdutoProcedimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/tipoProdutoProcedimento")
public class TipoProdutoProcedimentoResource {

    @Autowired
    private TipoProdutoProcedimentoService service;

    @GetMapping
    public ResponseEntity<List<TipoProdutoProcedimento>> findAll() {
        List<TipoProdutoProcedimento> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<TipoProdutoProcedimento> insert(@Valid @RequestBody TipoProdutoProcedimentoRecord obj) {
        TipoProdutoProcedimento fp = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fp.getId()).toUri();
        return ResponseEntity.created(uri).body(fp);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TipoProdutoProcedimento> findById(@PathVariable Long id){
        TipoProdutoProcedimento obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TipoProdutoProcedimento> update(@PathVariable Long id, @RequestBody TipoProdutoProcedimentoRecord obj) {
        TipoProdutoProcedimento entidade = service.update(id, obj);
        return ResponseEntity.ok().body(entidade);
    }

}
