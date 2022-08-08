package com.vr.miniautorizador.util;

import com.vr.miniautorizador.dto.CartaoDTO;
import com.vr.miniautorizador.dto.TransacaoDTO;
import com.vr.miniautorizador.entity.CartaoEntity;

public final class MiniAutorizadorUtils {
    
    private MiniAutorizadorUtils(){
        super();
    }
    
    public static CartaoDTO CartaoEntityParaDTO(CartaoEntity cartaoEntity){
        CartaoDTO cartaoDTO = new CartaoDTO(cartaoEntity.getSenha(),
                                            cartaoEntity.getNumeroCartao(),
                                            cartaoEntity.getSaldo());
        return cartaoDTO;
        
    }

    public static CartaoEntity cartaoDTOParaEntity(CartaoDTO cartaoDTO){
        CartaoEntity cartaoEntity = new CartaoEntity(null,
                                                    cartaoDTO.getSenha(),
                                                    cartaoDTO.getNumeroCartao(),
                                                    cartaoDTO.getSaldo());

        return cartaoEntity;
    }

    public static CartaoDTO transacaDTOParaCartaoDTO(TransacaoDTO transacaoDTO) {
        CartaoDTO cartaoDTO = new CartaoDTO(transacaoDTO.getSenhaCartao(),
                                            transacaoDTO.getNumeroCartao(),
                                            null);
        return cartaoDTO;
    }
}
