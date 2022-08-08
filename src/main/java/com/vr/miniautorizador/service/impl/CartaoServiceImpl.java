package com.vr.miniautorizador.service.impl;

import java.math.BigDecimal;
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
import com.vr.miniautorizador.exception.SaldoInsuficienteException;
import com.vr.miniautorizador.exception.SenhaInvalidaException;
import com.vr.miniautorizador.service.CartaoService;
import com.vr.miniautorizador.util.MiniAutorizadorUtils;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class CartaoServiceImpl  implements CartaoService {

    

    private CartaoDAO cartaoDAO;

    public CartaoServiceImpl(CartaoDAO cartaoDAO) {
        this.cartaoDAO = cartaoDAO;
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

        for(CartaoEntity miniAutorizadorEntity : cartaoDAO.findAll()){
            cartaoDTOList.add(MiniAutorizadorUtils.CartaoEntityParaDTO(miniAutorizadorEntity));
        }
        return cartaoDTOList;
    }

    @Override
    public CartaoDTO insereCartao(CartaoDTO cartaoDTO) throws CartaoJaExistenteException {
        
        if(cartaoDTO == null){
            throw new CartaoInexistenteExeception();
        }

        CartaoEntity cartaoEntity = getCartaoPorNumeroCartao(cartaoDTO.getNumCartao());

        if(cartaoEntity != null)
            throw new CartaoJaExistenteException();

        try{
            cartaoDAO.save(MiniAutorizadorUtils.cartaoDTOParaEntity(cartaoDTO));
        }catch(Exception e){
            e.printStackTrace();
            throw new MiniAutorizadorException();
        }
        return cartaoDTO;
        
    }

    @Override
    public CartaoDTO atualizaSaldoCartao(CartaoDTO cartaoDTO) {
        
        if(cartaoDTO == null){
            throw new CartaoInexistenteExeception();
        }

        CartaoEntity cartaoEntity = getCartaoPorNumeroCartao(cartaoDTO.getNumCartao());
        verificaSenha(cartaoEntity, cartaoDTO.getSenha()); 
  
        return MiniAutorizadorUtils.CartaoEntityParaDTO(cartaoEntity);
        
    }

    @Override
    public BigDecimal subtraiSaldoCartao(CartaoDTO cartaoDTO, BigDecimal valor) {
        
        if(cartaoDTO == null){
            throw new CartaoInexistenteExeception();
        }

        if(valor == null){
            valor = BigDecimal.ZERO;
        }

        CartaoEntity cartaoEntity = getCartaoPorNumeroCartao(cartaoDTO.getNumCartao());
        verificaSenha(cartaoEntity, cartaoDTO.getSenha()); 
        
        if(cartaoEntity.getSaldo().subtract(valor).compareTo(BigDecimal.ZERO) >= 0){
            cartaoDAO.save(cartaoEntity);
        }else{
            throw new SaldoInsuficienteException();
        }
  
        return cartaoEntity.getSaldo();
        
    }

    @Override
    public CartaoDTO getSaldoCartao(String numeroCartao) {
        CartaoEntity miniAutorizadorEntity = 
                cartaoDAO.findByNumCartao(numeroCartao);

        if(miniAutorizadorEntity == null)
            throw new CartaoInexistenteExeception();

        return MiniAutorizadorUtils.CartaoEntityParaDTO(miniAutorizadorEntity);
    }

    private CartaoEntity getCartaoPorNumeroCartao(String numeroCartao){
        if(numeroCartao == null){
            throw new CartaoInexistenteExeception();
        }

        CartaoEntity cartaoEntity = 
                cartaoDAO.findByNumCartao(numeroCartao);

        if(cartaoEntity == null)
            throw new CartaoInexistenteExeception();

        return cartaoEntity;
    }

    private Boolean verificaSenha(CartaoEntity cartaoEntity, String senha) {
        
        if(senha != null && cartaoEntity.getSenha().equals(senha))
                return Boolean.TRUE;
            else
                throw new SenhaInvalidaException();
    }

    
}
