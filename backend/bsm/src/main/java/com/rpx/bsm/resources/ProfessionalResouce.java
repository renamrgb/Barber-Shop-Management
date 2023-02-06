package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ProfissionalDTO;
import com.rpx.bsm.entities.Professional;
import com.rpx.bsm.records.ProfessionalRecord;
import com.rpx.bsm.services.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<ProfissionalDTO>> findAll(@RequestParam(defaultValue = "", name = "name") String name) {
        List<Professional> list = service.find(name);
        List<ProfissionalDTO> listDto = list.stream().map(x -> new ProfissionalDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProfissionalDTO> findById(@PathVariable Long id){
        Professional obj = service.findById(id);
        return ResponseEntity.ok().body(new ProfissionalDTO(obj));
    }

    @PostMapping
    public ResponseEntity<ProfissionalDTO> insert(@Valid @RequestBody ProfessionalRecord obj) {
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
    public ResponseEntity<ProfissionalDTO> update(@PathVariable Long id, @RequestBody ProfessionalRecord obj) {
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
