package com.rpx.bsm.resources;

import com.rpx.bsm.dto.CustomerDTO;
import com.rpx.bsm.dto.PaymentMethodDTO;
import com.rpx.bsm.dto.ProcedureDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/paymentMethod")
public class PaymentMethodResource {

    @Autowired
    private PaymentMethodService service;

    @GetMapping
    public ResponseEntity<List<PaymentMethodDTO>> find(@RequestParam(defaultValue = "", name = "description") String description) {
        List<PaymentMethod> list = service.find(description);
        List<PaymentMethodDTO> listDto = list.stream().map(x -> new PaymentMethodDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<Page<PaymentMethodDTO>> findPaged(@RequestParam(defaultValue = "", name = "description") String description, Pageable pageable) {
        Page<PaymentMethod> list = service.findPaged(description, pageable);
        Page<PaymentMethodDTO> listDto = list.map(x -> new PaymentMethodDTO(x));
        return ResponseEntity.ok().body(listDto);
    }

    @PostMapping
    public ResponseEntity<PaymentMethodDTO> insert(@Valid @RequestBody PaymentMethodRecord obj) {
        PaymentMethod fp = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fp.getId()).toUri();
        return ResponseEntity.created(uri).body(new PaymentMethodDTO(fp));
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
    public ResponseEntity<PaymentMethodDTO> update(@PathVariable Long id, @RequestBody PaymentMethodRecord obj) {
        PaymentMethod entidade = service.update(id, obj);
        return ResponseEntity.ok().body(new PaymentMethodDTO(entidade));
    }

}
