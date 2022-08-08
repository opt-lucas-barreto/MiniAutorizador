package com.vr.miniautorizador.service.impl;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vr.miniautorizador.dao.MiniAutorizadorDAO;
import com.vr.miniautorizador.dto.MiniAutorizadorDTO;
import com.vr.miniautorizador.entity.MiniAutorizadorEntity;
import com.vr.miniautorizador.exception.CartaoInexistenteExeception;
import com.vr.miniautorizador.exception.CartaoJaExistenteException;
import com.vr.miniautorizador.exception.MiniAutorizadorException;
import com.vr.miniautorizador.exception.SenhaInvalidaException;
import com.vr.miniautorizador.service.MiniAutorizadorService;
import com.vr.miniautorizador.util.MiniAutorizadorUtils;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class MiniAutorizadorServiceImpl  implements MiniAutorizadorService {

    

    private MiniAutorizadorDAO miniAutorizadorDAO;

    public MiniAutorizadorServiceImpl(MiniAutorizadorDAO miniAutorizadorDAO) {
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
    public List<MiniAutorizadorDTO> getAll() {
        List<MiniAutorizadorDTO> miniAutorizadorDTOList = new ArrayList<>();

        for(MiniAutorizadorEntity miniAutorizadorEntity : miniAutorizadorDAO.findAll()){
            miniAutorizadorDTOList.add(MiniAutorizadorUtils.miniAutorizadorEntityParaDTO(miniAutorizadorEntity));
        }
        return miniAutorizadorDTOList;
    }

    @Override
    public MiniAutorizadorDTO insereCartao(MiniAutorizadorDTO miniAutorizadorDTO) throws CartaoJaExistenteException {
        
        MiniAutorizadorEntity miniAutorizadorEntity = 
                miniAutorizadorDAO.findByNumCartao(miniAutorizadorDTO.getNumCartao());

        if(miniAutorizadorEntity == null)
            throw new CartaoJaExistenteException();

        try{
            miniAutorizadorDAO.save(MiniAutorizadorUtils.miniAutorizadorDTOParaEntity(miniAutorizadorDTO));
        }catch(Exception e){
            e.printStackTrace();
            throw new MiniAutorizadorException();
        }
        return miniAutorizadorDTO;
        
    }

    @Override
    public MiniAutorizadorDTO atualizaSaldoCartao(MiniAutorizadorDTO miniAutorizadorDTO) {
        
        if(miniAutorizadorDTO == null || miniAutorizadorDTO.getNumCartao() == null){
            throw new CartaoInexistenteExeception();
        }

        MiniAutorizadorEntity miniAutorizadorEntity = 
                miniAutorizadorDAO.findByNumCartao(miniAutorizadorDTO.getNumCartao());

        if(miniAutorizadorEntity == null)
            throw new CartaoInexistenteExeception();

        if(miniAutorizadorEntity.getSenha().equals(miniAutorizadorDTO.getSenha()))
            miniAutorizadorEntity = miniAutorizadorDAO.save(miniAutorizadorEntity);
        else
            throw new SenhaInvalidaException();
    
        
    
        return MiniAutorizadorUtils.miniAutorizadorEntityParaDTO(miniAutorizadorEntity);
        
    }

   
    
}
