package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ProfissionalDTO;
import com.rpx.bsm.entities.Profissional;
import com.rpx.bsm.records.ProfissionalRecord;
import com.rpx.bsm.services.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/profissionais")
public class ProfissionalResoucrce {

    @Autowired
    private ProfessionalService service;

    @GetMapping
    public ResponseEntity<List<ProfissionalDTO>> findAll() {
        List<ProfissionalDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Profissional> findById(@PathVariable Long id){
        Profissional obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<ProfissionalDTO> insert(@Valid @RequestBody ProfissionalRecord obj) {
        ProfissionalDTO dto = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProfissionalDTO> update(@PathVariable Long id, @RequestBody ProfissionalRecord obj) {
        ProfissionalDTO objDto = service.update(id, obj);
        return ResponseEntity.ok().body(objDto);
    }

    public static class ResourceNotFoundException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }

    }

}
