package com.vr.miniautorizador.service;

import java.math.BigDecimal;

import com.vr.miniautorizador.dto.TransacaoDTO;

import io.github.bucket4j.Bucket;

public interface TransacaoService {
    
    Bucket getBucketRateLimit();
    BigDecimal realizaTransacaoCompra(TransacaoDTO transacaoDTO);
}
