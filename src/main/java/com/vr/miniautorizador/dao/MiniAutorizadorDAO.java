package com.vr.miniautorizador.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.vr.miniautorizador.entity.MiniAutorizadorEntity;



@Repository
public interface MiniAutorizadorDAO extends JpaRepository<MiniAutorizadorEntity, Long>{
    
    //List<MiniAutorizadorEntity> findByNumCartao(String numCartao);
    MiniAutorizadorEntity findByNumCartao(String numCartao);
}
