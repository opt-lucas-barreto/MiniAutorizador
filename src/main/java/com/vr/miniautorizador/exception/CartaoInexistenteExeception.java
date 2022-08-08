package com.vr.miniautorizador.exception;

public class CartaoInexistenteExeception extends RuntimeException {
    private static final String CARTAO_INEXISTENTE = "CARTAO_INEXISTENTE";

    public CartaoInexistenteExeception(){
        super(CARTAO_INEXISTENTE);
    }

    public CartaoInexistenteExeception(String message, Throwable cause){
        super(message, cause);
    }

    public CartaoInexistenteExeception(String message){
        super(message);
    }

}
