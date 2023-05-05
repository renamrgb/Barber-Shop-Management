package com.rpx.bsm.resources;

import com.rpx.bsm.dto.BlockedTimesDTO;
import com.rpx.bsm.entities.BlockedTimes;
import com.rpx.bsm.records.BlockedTimesRecord;
import com.rpx.bsm.services.BlockedTimesService;
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
@RequestMapping(value = "/blockedTimes")
public class BlockedTimesResource {

    @Autowired
    private BlockedTimesService service;

    @GetMapping
    public ResponseEntity<Page<BlockedTimesDTO>> findPage(@RequestParam(defaultValue = "", name = "description") String desciption, Pageable pageable) {
        Page<BlockedTimes> list = service.find(desciption, pageable);
        Page<BlockedTimesDTO> listDto = list.map(x -> new BlockedTimesDTO(x));
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BlockedTimesDTO> findById(@PathVariable Long id){
        BlockedTimes obj = service.findById(id);
        return ResponseEntity.ok().body(new BlockedTimesDTO(obj));
    }

    @PostMapping
    public ResponseEntity<BlockedTimesDTO> insert(@Valid @RequestBody BlockedTimesRecord obj) {
        BlockedTimes cli = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cli.getId()).toUri();
        return ResponseEntity.created(uri).body(new BlockedTimesDTO(cli));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BlockedTimesDTO> update(@PathVariable Long id, @RequestBody BlockedTimesRecord record) {
        BlockedTimes obj = service.update(id, record);
        return ResponseEntity.ok().body(new BlockedTimesDTO(obj));
    }

    public static class ResourceNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public ResourceNotFoundException(Object id) {
            super("Resouce not found. Id " + id);
        }

    }


}
