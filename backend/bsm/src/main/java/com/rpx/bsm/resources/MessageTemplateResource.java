package com.rpx.bsm.resources;

import com.rpx.bsm.entities.MessageTemplate;
import com.rpx.bsm.records.MessageTemplateRecord;
import com.rpx.bsm.services.MessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/messageTemplate")
public class MessageTemplateResource {

    @Autowired
    private MessageTemplateService service;

    @GetMapping
    public ResponseEntity<List<MessageTemplate>> find(@RequestParam(defaultValue = "",name = "title") String title) {
        List<MessageTemplate> list = service.find(title);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<MessageTemplate> insert(@Valid @RequestBody MessageTemplateRecord record) {
        MessageTemplate obj = service.insert(record);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MessageTemplate> findById(@PathVariable Long id){
        MessageTemplate obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MessageTemplate> update(@PathVariable Long id, @Valid @RequestBody MessageTemplateRecord record) {
        MessageTemplate obj = service.update(id, record);
        return ResponseEntity.ok().body(obj);
    }

    public static class ResourceNotFoundException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }

    }

}
