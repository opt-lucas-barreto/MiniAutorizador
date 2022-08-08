package com.vr.miniautorizador.controller;

import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vr.miniautorizador.dto.MiniAutorizadorDTO;
import com.vr.miniautorizador.exception.CartaoJaExistenteException;
import com.vr.miniautorizador.service.MiniAutorizadorService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;

@RestController
@RequestMapping("/")
public class MiniAutorizadorController {
    
    /**
     * bucket para gestão de chamadas com token
    */
    Bucket bucketRateLimite = null; 

    @Autowired
    MiniAutorizadorService miniAutorizadorService;

    @GetMapping
    public ResponseEntity<?> testServer(){
        return ResponseEntity.ok("Server On");
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok("traz todos os cartões cadastrados");
    }

    @PostMapping("/cartoes")
    public ResponseEntity<?> setCartao(@Valid @RequestBody MiniAutorizadorDTO miniAutorizadorDTO){
        if(bucketRateLimite == null){
            bucketRateLimite = miniAutorizadorService.getBucketRateLimit();
        }
        
        if(bucketRateLimite.tryConsume(1)){
            try {
                miniAutorizadorService.insereCartao(miniAutorizadorDTO);
                return mountResponse(miniAutorizadorDTO, HttpStatus.OK);
            } catch (CartaoJaExistenteException e) {
                return mountResponse(miniAutorizadorDTO, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }else{
            return mountResponse(miniAutorizadorDTO, HttpStatus.TOO_MANY_REQUESTS);
        }
            
    }
    
    private ResponseEntity<?> mountResponse(MiniAutorizadorDTO miniAutorizadorDTO, HttpStatus status){
        final HttpHeaders responseHeaders = new HttpHeaders();
        if(bucketRateLimite.getAvailableTokens() <= 0){
            
            ConsumptionProbe probe = bucketRateLimite.tryConsumeAndReturnRemaining(1);
            responseHeaders.set("X-RateLimit-Reset", ""+TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill()));
            
            ResponseEntity<MiniAutorizadorDTO> response = 
                new ResponseEntity<MiniAutorizadorDTO>(miniAutorizadorDTO, responseHeaders, status);
        
            return response;
        }else{

            responseHeaders.set("X-RateLimit-Remaining", ""+bucketRateLimite.getAvailableTokens());
            ResponseEntity<MiniAutorizadorDTO> response = 
                new ResponseEntity<MiniAutorizadorDTO>(miniAutorizadorDTO, responseHeaders, status);
            return response;
        }

    }

}
