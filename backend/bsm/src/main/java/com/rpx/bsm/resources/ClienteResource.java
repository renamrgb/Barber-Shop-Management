package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ClienteDTO;
import com.rpx.bsm.entities.Cliente;
import com.rpx.bsm.records.ClienteRecord;
import com.rpx.bsm.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> insert(@Valid @RequestBody ClienteRecord obj) {
        ClienteDTO cli = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cli.getId()).toUri();
        return ResponseEntity.created(uri).body(cli);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteRecord obj) {
        ClienteDTO objDto = service.update(id, obj);
        return ResponseEntity.ok().body(objDto);
    }

    public static class ResourceNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }

    }


}
