package com.vr.miniautorizador.service.impl;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vr.miniautorizador.dao.CartaoDAO;
import com.vr.miniautorizador.dto.CartaoDTO;
import com.vr.miniautorizador.entity.CartaoEntity;
import com.vr.miniautorizador.exception.CartaoInexistenteExeception;
import com.vr.miniautorizador.exception.CartaoJaExistenteException;
import com.vr.miniautorizador.exception.MiniAutorizadorException;
import com.vr.miniautorizador.exception.SenhaInvalidaException;
import com.vr.miniautorizador.service.CartaoService;
import com.vr.miniautorizador.util.MiniAutorizadorUtils;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class CartaoServiceImpl  implements CartaoService {

    

    private CartaoDAO miniAutorizadorDAO;

    public CartaoServiceImpl(CartaoDAO miniAutorizadorDAO) {
        this.miniAutorizadorDAO = miniAutorizadorDAO;
    }

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
    public List<CartaoDTO> getAll() {
        List<CartaoDTO> cartaoDTOList = new ArrayList<>();

        for(CartaoEntity miniAutorizadorEntity : miniAutorizadorDAO.findAll()){
            cartaoDTOList.add(MiniAutorizadorUtils.miniAutorizadorEntityParaDTO(miniAutorizadorEntity));
        }
        return cartaoDTOList;
    }

    @Override
    public CartaoDTO insereCartao(CartaoDTO cartaoDTO) throws CartaoJaExistenteException {
        
        CartaoEntity miniAutorizadorEntity = 
                miniAutorizadorDAO.findByNumCartao(cartaoDTO.getNumCartao());

        if(miniAutorizadorEntity == null)
            throw new CartaoJaExistenteException();

        try{
            miniAutorizadorDAO.save(MiniAutorizadorUtils.cartaoDTOParaEntity(cartaoDTO));
        }catch(Exception e){
            e.printStackTrace();
            throw new MiniAutorizadorException();
        }
        return cartaoDTO;
        
    }

    @Override
    public CartaoDTO atualizaSaldoCartao(CartaoDTO cartaoDTO) {
        
        if(cartaoDTO == null || cartaoDTO.getNumCartao() == null){
            throw new CartaoInexistenteExeception();
        }

        CartaoEntity miniAutorizadorEntity = 
                miniAutorizadorDAO.findByNumCartao(cartaoDTO.getNumCartao());

        if(miniAutorizadorEntity == null)
            throw new CartaoInexistenteExeception();

        if(miniAutorizadorEntity.getSenha().equals(cartaoDTO.getSenha()))
            miniAutorizadorEntity = miniAutorizadorDAO.save(miniAutorizadorEntity);
        else
            throw new SenhaInvalidaException();
    
        
    
        return MiniAutorizadorUtils.miniAutorizadorEntityParaDTO(miniAutorizadorEntity);
        
    }

    @Override
    public CartaoDTO getSaldoCartao(String numeroCartao) {
        CartaoEntity miniAutorizadorEntity = 
                miniAutorizadorDAO.findByNumCartao(numeroCartao);

        if(miniAutorizadorEntity == null)
            throw new CartaoInexistenteExeception();

        return MiniAutorizadorUtils.miniAutorizadorEntityParaDTO(miniAutorizadorEntity);
    }


    
}
