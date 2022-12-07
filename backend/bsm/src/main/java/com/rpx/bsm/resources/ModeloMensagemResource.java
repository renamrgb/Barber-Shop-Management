package com.rpx.bsm.resources;

import com.rpx.bsm.entities.ModeloMensagem;
import com.rpx.bsm.records.ModeloMensagemRecord;
import com.rpx.bsm.services.ModeloMensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/modeloMensagens")
public class ModeloMensagemResource {

    @Autowired
    private ModeloMensagemService service;

    @GetMapping
    public ResponseEntity<List<ModeloMensagem>> findAll() {
        List<ModeloMensagem> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<ModeloMensagem> insert(@Valid @RequestBody ModeloMensagemRecord record) {
        ModeloMensagem obj = service.insert(record);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ModeloMensagem> findById(@PathVariable Long id){
        ModeloMensagem obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ModeloMensagem> update(@PathVariable Long id, @Valid @RequestBody ModeloMensagemRecord record) {
        ModeloMensagem obj = service.update(id, record);
        return ResponseEntity.ok().body(obj);
    }

    public static class ResourceNotFoundException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }

    }

}
