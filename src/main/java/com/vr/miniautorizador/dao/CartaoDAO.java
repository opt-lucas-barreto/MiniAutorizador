package com.vr.miniautorizador.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.vr.miniautorizador.entity.CartaoEntity;



@Repository
public interface CartaoDAO extends JpaRepository<CartaoEntity, Long>{
    
    //List<MiniAutorizadorEntity> findByNumCartao(String numCartao);
    CartaoEntity findByNumCartao(String numCartao);
}
