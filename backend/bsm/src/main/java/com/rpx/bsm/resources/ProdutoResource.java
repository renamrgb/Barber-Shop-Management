package com.rpx.bsm.resources;

import com.rpx.bsm.entities.Produto;
import com.rpx.bsm.records.ProdutoRecord;
import com.rpx.bsm.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        List<Produto> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id){
        Produto obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Produto> insert(@Valid @RequestBody ProdutoRecord record) {
        Produto obj = service.insert(record);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @Valid @RequestBody ProdutoRecord record) {
        Produto obj = service.update(id, record);
        return ResponseEntity.ok().body(obj);
    }

    public static class ResourceNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }
    }

}