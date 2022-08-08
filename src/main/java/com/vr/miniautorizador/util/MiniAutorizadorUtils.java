package com.vr.miniautorizador.util;

import com.vr.miniautorizador.dto.MiniAutorizadorDTO;
import com.vr.miniautorizador.entity.MiniAutorizadorEntity;

public final class MiniAutorizadorUtils {
    
    private MiniAutorizadorUtils(){
        super();
    }
    
    public static MiniAutorizadorDTO miniAutorizadorEntityParaDTO(MiniAutorizadorEntity miniAutorizadorEntity){
        MiniAutorizadorDTO miniAutorizadorDTO = new MiniAutorizadorDTO(miniAutorizadorEntity.getSenha(),
                                                                       miniAutorizadorEntity.getNumCartao(),
                                                                       miniAutorizadorEntity.getSaldo());
        return miniAutorizadorDTO;
        
    }

    public static MiniAutorizadorEntity miniAutorizadorDTOParaEntity(MiniAutorizadorDTO miniAutorizadorDTO){
        MiniAutorizadorEntity miniAutorizadorEntity = new MiniAutorizadorEntity(null,
                                                                                miniAutorizadorDTO.getSenha(),
                                                                                miniAutorizadorDTO.getNumCartao(),
                                                                                miniAutorizadorDTO.getSaldo());

        return miniAutorizadorEntity;
    }
}
