package com.vr.miniautorizador.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vr.miniautorizador.dto.CartaoDTO;
import com.vr.miniautorizador.dto.TransacaoDTO;
import com.vr.miniautorizador.service.CartaoService;
import com.vr.miniautorizador.service.TransacaoService;
import com.vr.miniautorizador.util.MiniAutorizadorUtils;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class TransacaoServiceImpl implements TransacaoService{
    
    @Autowired
    CartaoService cartaoService;

    @Override
    public Bucket getBucketRateLimit() {
        Bandwidth bandwidth = Bandwidth.classic(1, 
                                                Refill.intervallyAligned(1, 
                                                Duration.ofSeconds(5),
                                                ZonedDateTime.now().toInstant(), 
                                                true));
        return  Bucket.builder().addLimit(bandwidth).build();
    }

    @Override
    public BigDecimal realizaTransacaoCompra(TransacaoDTO transacaoDTO) {
        CartaoDTO cartaoDTO = MiniAutorizadorUtils.transacaDTOParaCartaoDTO(transacaoDTO);
        return cartaoService.subtraiSaldoCartao(cartaoDTO, transacaoDTO.getValorTransacao());
    }
}
