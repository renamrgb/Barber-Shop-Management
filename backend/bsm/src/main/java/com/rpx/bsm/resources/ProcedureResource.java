package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ProcedureDTO;
import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.records.ProcedureRecord;
import com.rpx.bsm.services.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/procedures")
public class ProcedureResource {
    @Autowired
    private ProcedureService service;

    @GetMapping
    public ResponseEntity<List<Procedure>> find(@RequestParam(defaultValue = "", name = "description") String description) {
        List<Procedure> list = service.find(description);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Procedure> findById(@PathVariable Long id){
        Procedure obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<ProcedureDTO> insert(@Valid @RequestBody ProcedureRecord record) {
        ProcedureDTO obj = service.insert(record);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProcedureDTO> update(@PathVariable Long id, @Valid @RequestBody ProcedureRecord record) {
        ProcedureDTO obj = service.update(id, record);
        return ResponseEntity.ok().body(obj);
    }

    public static class ResourceNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }
    }

}
