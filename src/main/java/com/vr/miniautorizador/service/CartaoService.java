package com.vr.miniautorizador.service;

import java.util.List;

import com.vr.miniautorizador.dto.CartaoDTO;
import com.vr.miniautorizador.exception.CartaoJaExistenteException;

import io.github.bucket4j.Bucket;

public interface CartaoService {
    
    Bucket getBucketRateLimit();
    List<CartaoDTO> getAll();
    CartaoDTO insereCartao(CartaoDTO cartaoDTO) throws CartaoJaExistenteException;
    CartaoDTO atualizaSaldoCartao(CartaoDTO cartaoDTO);
    CartaoDTO getSaldoCartao(String numeroCartao);
    
}
