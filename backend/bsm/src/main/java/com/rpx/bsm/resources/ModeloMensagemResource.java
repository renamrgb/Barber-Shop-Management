package com.rpx.bsm.resources;

import com.rpx.bsm.entities.ModeloMensagem;
import com.rpx.bsm.entities.Produto;
import com.rpx.bsm.services.ModeloMensagemService;
import com.rpx.bsm.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/procedimentos")
public class ModeloMensagemResource {

    @Autowired
    private ModeloMensagemService service;

    @GetMapping
    public ResponseEntity<List<ModeloMensagem>> findAll() {
        List<ModeloMensagem> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<ModeloMensagem> insert(@RequestBody ModeloMensagem obj) {
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
    public ResponseEntity<ModeloMensagem> update(@PathVariable Long id, @RequestBody ModeloMensagem obj) {
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
