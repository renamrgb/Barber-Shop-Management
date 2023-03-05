package com.rpx.bsm.resources;

import com.rpx.bsm.entities.ProductProcedureType;
import com.rpx.bsm.records.ProductProcedureTypesRecord;
import com.rpx.bsm.services.ProductProcedureTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/productProcedureTypes")
public class ProductProcedureTypesResource {

    @Autowired
    private ProductProcedureTypesService service;

    @GetMapping
    public ResponseEntity<List<ProductProcedureType>> find(@RequestParam(defaultValue = "",name = "description") String description) {
        List<ProductProcedureType> list = service.find(description);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<Page<ProductProcedureType>> findPaged(@RequestParam(defaultValue = "",name = "description") String description, Pageable pageable) {
        Page<ProductProcedureType> list = service.findPaged(description, pageable);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<ProductProcedureType> insert(@Valid @RequestBody ProductProcedureTypesRecord obj) {
        ProductProcedureType fp = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fp.getId()).toUri();
        return ResponseEntity.created(uri).body(fp);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductProcedureType> findById(@PathVariable Long id){
        ProductProcedureType obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductProcedureType> update(@PathVariable Long id, @RequestBody ProductProcedureTypesRecord obj) {
        ProductProcedureType entidade = service.update(id, obj);
        return ResponseEntity.ok().body(entidade);
    }

}
