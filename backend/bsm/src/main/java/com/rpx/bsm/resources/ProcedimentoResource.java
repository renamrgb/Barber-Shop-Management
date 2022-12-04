package com.rpx.bsm.resources;

import com.rpx.bsm.entities.Procedimento;
import com.rpx.bsm.services.ProcedimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/procedimentos")
public class ProcedimentoResource {
    @Autowired
    private ProcedimentoService service;

    @GetMapping
    public ResponseEntity<List<Procedimento>> findAll() {
        List<Procedimento> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

//    @PostMapping
//    public ResponseEntity<Procedimento> insert(@RequestBody ProcedimentoRecord obj) {
//        obj = service.insert(obj);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
//        return ResponseEntity.ok().body(obj);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Procedimento> update(@PathVariable Long id, @RequestBody ProcedimentoRecord obj) {
//        obj = service.update(id, obj);
//        return ResponseEntity.ok().body(obj);
//    }

    public static class ResourceNotFoundException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }

    }

}
