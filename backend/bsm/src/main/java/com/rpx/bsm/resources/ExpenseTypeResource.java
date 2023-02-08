package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ExpenseTypeDTO;
import com.rpx.bsm.entities.ExpenseType;
import com.rpx.bsm.records.ExpenseTypeRecord;
import com.rpx.bsm.services.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/expenseTypes")
public class ExpenseTypeResource {

    @Autowired
    private ExpenseTypeService service;

    @GetMapping
    public ResponseEntity<List<ExpenseTypeDTO>> find(@RequestParam(defaultValue = "",name = "description") String description) {
        List<ExpenseType> list = service.find(description);
        List<ExpenseTypeDTO> listDto = list.stream().map(x -> new ExpenseTypeDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @PostMapping
    public ResponseEntity<ExpenseTypeDTO> insert(@Valid @RequestBody ExpenseTypeRecord obj) {
        ExpenseType fp = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fp.getId()).toUri();
        return ResponseEntity.created(uri).body(new ExpenseTypeDTO(fp));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpenseTypeDTO> findById(@PathVariable Long id){
        ExpenseType obj = service.findById(id);
        return ResponseEntity.ok().body(new ExpenseTypeDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ExpenseTypeDTO> update(@PathVariable Long id, @RequestBody ExpenseTypeRecord obj) {
        ExpenseType entity = service.update(id, obj);
        return ResponseEntity.ok().body(new ExpenseTypeDTO(entity));
    }

}
