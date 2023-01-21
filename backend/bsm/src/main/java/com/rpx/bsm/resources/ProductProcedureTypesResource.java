package com.rpx.bsm.resources;

import com.rpx.bsm.entities.ProductProcedureTypes;
import com.rpx.bsm.records.ProductProcedureTypesRecord;
import com.rpx.bsm.services.ProductProcedureTypesService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<ProductProcedureTypes>> find(@RequestParam(defaultValue = "",name = "description") String description) {
        List<ProductProcedureTypes> list = service.find(description);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<ProductProcedureTypes> insert(@Valid @RequestBody ProductProcedureTypesRecord obj) {
        ProductProcedureTypes fp = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fp.getId()).toUri();
        return ResponseEntity.created(uri).body(fp);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductProcedureTypes> findById(@PathVariable Long id){
        ProductProcedureTypes obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductProcedureTypes> update(@PathVariable Long id, @RequestBody ProductProcedureTypesRecord obj) {
        ProductProcedureTypes entidade = service.update(id, obj);
        return ResponseEntity.ok().body(entidade);
    }

}
