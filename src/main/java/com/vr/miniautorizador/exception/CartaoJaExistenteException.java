package com.vr.miniautorizador.exception;

public class CartaoJaExistenteException extends Exception{
    private static final String CARTAO_JA_EXISTENTE = "CARTAO_JA_EXISTENTE";

    public CartaoJaExistenteException(){
        super(CARTAO_JA_EXISTENTE);
    }

    public CartaoJaExistenteException(String message, Throwable cause){
        super(message, cause);
    }

    public CartaoJaExistenteException(String message){
        super(message);
    }
}
