package com.vr.miniautorizador.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MiniAutorizadorEntity {
    
    
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long Id;

    @Getter @Setter
    private String senha;

    @Getter @Setter
    @Column(unique = true)
    private String numCartao;

    @Getter @Setter
    private BigDecimal saldo;

    

}
