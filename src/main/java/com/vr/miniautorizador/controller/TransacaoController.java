package com.vr.miniautorizador.controller;

import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vr.miniautorizador.dto.CartaoDTO;
import com.vr.miniautorizador.dto.TransacaoDTO;
import com.vr.miniautorizador.service.TransacaoService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;

@RestController
public class TransacaoController {
    
    /**
     * bucket para gestão de chamadas com token por domínio
    */
    Bucket bucketRateLimite = null; 

    @Autowired
    TransacaoService transacaoService;
    
    @PostMapping("/transacoes")
    public ResponseEntity<?> realizaTransacao(@Valid @RequestBody TransacaoDTO transacaoDTO){
        if(bucketRateLimite == null){
            bucketRateLimite = transacaoService.getBucketRateLimit();
        }
        if(bucketRateLimite.tryConsume(1)){
            transacaoService.realizaTransacaoCompra(transacaoDTO);
           return mountResponse("OK", HttpStatus.OK);
        }else{
            return mountResponse(transacaoDTO, HttpStatus.TOO_MANY_REQUESTS);
        }
        
    }

    private ResponseEntity<?> mountResponse(Object obj, HttpStatus status){
        final HttpHeaders responseHeaders = new HttpHeaders();
        if(bucketRateLimite.getAvailableTokens() <= 0){
            
            ConsumptionProbe probe = bucketRateLimite.tryConsumeAndReturnRemaining(1);
            responseHeaders.set("X-RateLimit-Reset", ""+TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill()));
            
            ResponseEntity<Object> response = 
                new ResponseEntity<Object>(obj, responseHeaders, status);
        
            return response;
        }else{

            responseHeaders.set("X-RateLimit-Remaining", ""+bucketRateLimite.getAvailableTokens());
            ResponseEntity<Object> response = 
                new ResponseEntity<Object>(obj, responseHeaders, status);
            return response;
        }

    }

}
