package com.rpx.bsm.resources;

import com.rpx.bsm.entities.Produto;
import com.rpx.bsm.entities.TipoDespesa;
import com.rpx.bsm.services.ProdutoService;
import com.rpx.bsm.services.TipoDespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/tipoDespesas")
public class TipoDespesaResource {

    @Autowired
    private TipoDespesaService service;

    @GetMapping
    public ResponseEntity<List<TipoDespesa>> findAll() {
        List<TipoDespesa> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<TipoDespesa> insert(@RequestBody TipoDespesa obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TipoDespesa> update(@PathVariable Long id, @RequestBody TipoDespesa obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    public static class ResourceNotFoundException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }

    }

}
