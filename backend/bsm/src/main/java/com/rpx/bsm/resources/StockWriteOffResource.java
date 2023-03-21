package com.rpx.bsm.resources;

import com.rpx.bsm.dto.StockEntryDTO;
import com.rpx.bsm.dto.StockWriteOffDTO;
import com.rpx.bsm.entities.StockEntry;
import com.rpx.bsm.entities.StockWriteOff;
import com.rpx.bsm.records.StockEntryRecord;
import com.rpx.bsm.records.StockWriteOffRecord;
import com.rpx.bsm.services.StockEntryService;
import com.rpx.bsm.services.StockWriteOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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


}
