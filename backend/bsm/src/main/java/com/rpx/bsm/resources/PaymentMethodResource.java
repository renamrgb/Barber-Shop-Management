package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ProductDTO;
import com.rpx.bsm.entities.PaymentMethod;
import com.rpx.bsm.records.PaymentMethodRecord;
import com.rpx.bsm.services.PaymentMethodService;
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
@RequestMapping(value = "/paymentMethod")
public class PaymentMethodResource {

    @Autowired
    private PaymentMethodService service;

    @GetMapping
    public ResponseEntity<List<PaymentMethod>> find(@RequestParam(defaultValue = "", name = "description") String description) {
        List<PaymentMethod> list = service.find(description);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<Page<PaymentMethod>> findPaged(@RequestParam(defaultValue = "", name = "description") String description, Pageable pageable) {
        Page<PaymentMethod> list = service.findPaged(description, pageable);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<PaymentMethod> insert(@Valid @RequestBody PaymentMethodRecord obj) {
        PaymentMethod fp = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fp.getId()).toUri();
        return ResponseEntity.created(uri).body(fp);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaymentMethod> findById(@PathVariable Long id){
        PaymentMethod obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PaymentMethod> update(@PathVariable Long id, @RequestBody PaymentMethodRecord obj) {
        PaymentMethod entidade = service.update(id, obj);
        return ResponseEntity.ok().body(entidade);
    }

}
