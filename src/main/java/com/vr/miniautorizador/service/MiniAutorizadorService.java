package com.vr.miniautorizador.service;

import java.util.List;

import com.vr.miniautorizador.dto.MiniAutorizadorDTO;
import com.vr.miniautorizador.exception.CartaoJaExistenteException;

import io.github.bucket4j.Bucket;

public interface MiniAutorizadorService {
    
    Bucket getBucketRateLimit();
    List<MiniAutorizadorDTO> getAll();
    MiniAutorizadorDTO insereCartao(MiniAutorizadorDTO miniAutorizadorDTO) throws CartaoJaExistenteException;
    MiniAutorizadorDTO atualizaSaldoCartao(MiniAutorizadorDTO miniAutorizadorDTO);
}
