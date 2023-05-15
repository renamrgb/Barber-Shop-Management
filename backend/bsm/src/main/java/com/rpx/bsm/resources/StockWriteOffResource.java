package com.rpx.bsm.resources;

import com.rpx.bsm.dto.StockWriteOffDTO;
import com.rpx.bsm.entities.StockWriteOff;
import com.rpx.bsm.records.StockWriteOffRecord;
import com.rpx.bsm.services.StockWriteOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/stockWriteOff")
public class StockWriteOffResource {

    @Autowired
    private StockWriteOffService service;
    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody StockWriteOffRecord r) {
        StockWriteOff obj = service.insert(r);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(new StockWriteOffDTO(obj));
    }

    @GetMapping
    public ResponseEntity<Page<StockWriteOffDTO>> getAll(
            Pageable pageable,
            @RequestParam(name = "title", defaultValue = "") String title,
            @RequestParam(name = "reason", defaultValue = "") String reason,
            @RequestParam(name = "dtStart", defaultValue = "") String dtStart,
            @RequestParam(name = "dtEnd", defaultValue = "") String dtEnd
    ){
        Page<StockWriteOff> list = service.find(pageable, title, reason, dtStart, dtEnd);
        Page<StockWriteOffDTO> listDto = list.map(x -> new StockWriteOffDTO(x));
        return ResponseEntity.ok().body(listDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
