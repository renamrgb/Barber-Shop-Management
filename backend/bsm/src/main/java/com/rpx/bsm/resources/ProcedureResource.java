package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ProcedureDTO;
import com.rpx.bsm.dto.ProductDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/procedures")
public class ProcedureResource {
    @Autowired
    private ProcedureService service;

    @GetMapping
    public ResponseEntity<List<ProcedureDTO>> find(@RequestParam(defaultValue = "", name = "description") String description) {
        List<Procedure> list = service.find(description);
        List<ProcedureDTO> listDto = list.stream().map(x -> new ProcedureDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProcedureDTO> findById(@PathVariable Long id){
        Procedure obj = service.findById(id);
        return ResponseEntity.ok().body(new ProcedureDTO(obj));
    }

    @PostMapping
    public ResponseEntity<ProcedureDTO> insert(@Valid @RequestBody ProcedureRecord record) {
        Procedure obj = service.insert(record);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProcedureDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProcedureDTO> update(@PathVariable Long id, @Valid @RequestBody ProcedureRecord record) {
        Procedure obj = service.update(id, record);
        return ResponseEntity.ok().body(new ProcedureDTO(obj));
    }

    public static class ResourceNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }
    }

}
