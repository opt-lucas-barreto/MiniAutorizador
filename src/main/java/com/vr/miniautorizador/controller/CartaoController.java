package com.vr.miniautorizador.controller;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vr.miniautorizador.dto.CartaoDTO;
import com.vr.miniautorizador.exception.CartaoInexistenteExeception;
import com.vr.miniautorizador.exception.CartaoJaExistenteException;
import com.vr.miniautorizador.service.CartaoService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;

@RestController
@RequestMapping("/")
public class CartaoController {
    
    /**
     * bucket para gestão de chamadas com token
    */
    Bucket bucketRateLimite = null; 

    @Autowired
    CartaoService miniAutorizadorService;

    @GetMapping
    public ResponseEntity<?> testServer(){
        return ResponseEntity.ok("Server On");
    }

    @GetMapping("/cartoes")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok("traz todos os cartões cadastrados");
    }

    @PostMapping("/cartoes")
    public ResponseEntity<?> setCartao(@Valid @RequestBody CartaoDTO cartaoDTO){
        if(bucketRateLimite == null){
            bucketRateLimite = miniAutorizadorService.getBucketRateLimit();
        }
        
        if(bucketRateLimite.tryConsume(1)){
            try {
                miniAutorizadorService.insereCartao(cartaoDTO);
                return mountResponse(cartaoDTO, HttpStatus.CREATED);
            } catch (CartaoJaExistenteException e) {
                return mountResponse(cartaoDTO, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }else{
            return mountResponse(cartaoDTO, HttpStatus.TOO_MANY_REQUESTS);
        }
            
    }
    
    @GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<?> obterSaldo(@PathVariable String numeroCartao){
        BigDecimal saldoCartao = BigDecimal.ZERO;
        CartaoDTO cartaoDTO = null;
        try{
            cartaoDTO = miniAutorizadorService.getSaldoCartao(numeroCartao);
            saldoCartao = cartaoDTO.getSaldo();
            return mountResponse(saldoCartao, HttpStatus.OK);
        }catch(CartaoInexistenteExeception e){
            return ResponseEntity.notFound().build();
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
