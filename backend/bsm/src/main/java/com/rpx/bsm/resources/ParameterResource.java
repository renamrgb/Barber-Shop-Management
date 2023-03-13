package com.rpx.bsm.resources;

import com.rpx.bsm.dto.ExpenseDTO;
import com.rpx.bsm.dto.InstallmentDTO;
import com.rpx.bsm.dto.ParameterDTO;
import com.rpx.bsm.dto.ProcedureDTO;
import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.entities.Installment;
import com.rpx.bsm.entities.Parameter;
import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.records.ExpenseRecord;
import com.rpx.bsm.records.ParameterRecord;
import com.rpx.bsm.services.ExpenseService;
import com.rpx.bsm.services.ParameterService;
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
@RequestMapping(value = "/parameters")
public class ParameterResource {

    @Autowired
    private ParameterService service;

    @GetMapping
    public ResponseEntity<List<ParameterDTO>> findAll() {
        List<Parameter> list = service.findAll();
        List<ParameterDTO> listDto = list.stream().map(x -> new ParameterDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<ParameterDTO> update(@PathVariable Long id, @RequestBody ParameterRecord obj) {
        Parameter objDto = service.update(id, obj);
        return ResponseEntity.ok().body(new ParameterDTO(objDto));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ParameterDTO> findById(@PathVariable Long id){
        Parameter obj = service.findById(id);
        return ResponseEntity.ok().body(new ParameterDTO(obj));
    }

}
