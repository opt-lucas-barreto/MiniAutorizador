package com.vr.miniautorizador.util;

import com.vr.miniautorizador.dto.CartaoDTO;
import com.vr.miniautorizador.entity.CartaoEntity;

public final class MiniAutorizadorUtils {
    
    private MiniAutorizadorUtils(){
        super();
    }
    
    public static CartaoDTO miniAutorizadorEntityParaDTO(CartaoEntity miniAutorizadorEntity){
        CartaoDTO cartaoDTO = new CartaoDTO(miniAutorizadorEntity.getSenha(),
                                                                       miniAutorizadorEntity.getNumCartao(),
                                                                       miniAutorizadorEntity.getSaldo());
        return cartaoDTO;
        
    }

    public static CartaoEntity cartaoDTOParaEntity(CartaoDTO cartaoDTO){
        CartaoEntity miniAutorizadorEntity = new CartaoEntity(null,
                                                                                cartaoDTO.getSenha(),
                                                                                cartaoDTO.getNumCartao(),
                                                                                cartaoDTO.getSaldo());

        return miniAutorizadorEntity;
    }
}
