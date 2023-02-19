package com.rpx.bsm.resources;

import com.rpx.bsm.dto.AddressDTO;
import com.rpx.bsm.dto.ViaCEPResponse;
import com.rpx.bsm.services.AddressService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
@Validated
@RestController
@RequestMapping(value = "/address")
public class AddresResource {

    @Autowired
    private AddressService service;

    @GetMapping(value = "/{cep}")
    public ResponseEntity<AddressDTO> findByCep(@PathVariable("cep") @NotBlank @Length(max = 8, min = 8) String cep){
        ViaCEPResponse obj = service.getAddressByCep(cep);
        return ResponseEntity.ok().body(new AddressDTO(obj));
    }

}
