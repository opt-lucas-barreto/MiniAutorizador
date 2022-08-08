package com.vr.miniautorizador.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MiniAutorizadorDTO {
    
    @JsonProperty
    @NotNull(message = "nome não pode ser nulo")
    @NotBlank
    @Getter @Setter
    private String senha;


    @JsonProperty
    @NotNull(message = "Número do cartão não pode ser nulo")
    @NotBlank
    @Getter @Setter
    private String numCartao;

    @JsonIgnore
    @Getter @Setter
    private BigDecimal saldo;
    
}
