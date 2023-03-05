package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ProfessionalDTO;
import com.rpx.bsm.entities.Professional;
import com.rpx.bsm.records.ProfessionalRecord;
import com.rpx.bsm.services.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/professionals")
public class ProfessionalResouce {

    @Autowired
    private ProfessionalService service;

    @GetMapping
    public ResponseEntity<List<ProfessionalDTO>> findAll(@RequestParam(defaultValue = "", name = "name") String name) {
        List<Professional> list = service.find(name);
        List<ProfessionalDTO> listDto = list.stream().map(x -> new ProfessionalDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<Page<ProfessionalDTO>> findAllPaged(@RequestParam(defaultValue = "", name = "name") String name, Pageable pageable) {
        Page<Professional> list = service.findPaged(name, pageable);
        Page<ProfessionalDTO> listDto = list.map(x -> new ProfessionalDTO(x));
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProfessionalDTO> findById(@PathVariable Long id){
        Professional obj = service.findById(id);
        return ResponseEntity.ok().body(new ProfessionalDTO(obj));
    }

    @PostMapping
    public ResponseEntity<ProfessionalDTO> insert(@Valid @RequestBody ProfessionalRecord record) {
        Professional obj = service.insert(record);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProfessionalDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProfessionalDTO> update(@PathVariable Long id, @RequestBody ProfessionalRecord record) {
        Professional obj = service.update(id, record);
        return ResponseEntity.ok().body(new ProfessionalDTO(obj));
    }

    public static class ResourceNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }

    }

}
