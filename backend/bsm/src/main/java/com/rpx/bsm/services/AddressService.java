package com.rpx.bsm.services;

import com.rpx.bsm.dto.ViaCEPResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Slf4j
@Service
@AllArgsConstructor
public class AddressService {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://viacep.com.br/ws/%s/json/";
    @Cacheable(value = "VIACEP", key = "#cep")
    public ViaCEPResponse getAddressByCep(String cep){
        ResponseEntity<ViaCEPResponse> response = getCep(cep);
        log.info("SOLICITANDO CEP");
        return response.getBody();
    }
    private ResponseEntity<ViaCEPResponse> getCep(String cep){
        final HttpEntity<Object> entity = new HttpEntity<>(getHeaders());
        try {
            String requestUrl = String.format(BASE_URL, cep);
            return restTemplate.exchange(requestUrl, HttpMethod.GET, entity, ViaCEPResponse.class);
        }catch (Exception e){
            log.error("Erro ao recuperar CEP {}", cep);
            throw new RuntimeException(e);
        }
    }

    private Object getHeaders() {
        return new HttpHeaders();
    }
}
