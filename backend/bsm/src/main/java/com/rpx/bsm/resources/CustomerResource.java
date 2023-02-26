package com.rpx.bsm.resources;

import com.rpx.bsm.dto.CustomerDTO;
import com.rpx.bsm.entities.Customer;
import com.rpx.bsm.records.CustomerRecord;
import com.rpx.bsm.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {

    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll(@RequestParam(defaultValue = "", name = "name") String name) {
        List<Customer> list = service.find(name);
        List<CustomerDTO> listDto = list.stream().map(x -> new CustomerDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id){
        Customer obj = service.findById(id);
        return ResponseEntity.ok().body(new CustomerDTO(obj));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> insert(@Valid @RequestBody CustomerRecord obj) {
        Customer cli = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cli.getId()).toUri();
        return ResponseEntity.created(uri).body(new CustomerDTO(cli));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody CustomerRecord obj) {
        CustomerDTO objDto = service.update(id, obj);
        return ResponseEntity.ok().body(objDto);
    }

    public static class ResourceNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }

    }


}
