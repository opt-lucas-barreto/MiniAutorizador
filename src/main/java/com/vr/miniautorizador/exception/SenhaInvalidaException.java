package com.vr.miniautorizador.exception;

public class SenhaInvalidaException extends RuntimeException{
    private static final String SENHA_INVALIDA = "SENHA_INVALIDA";

    public SenhaInvalidaException(){
        super(SENHA_INVALIDA);
    }

    public SenhaInvalidaException(String message, Throwable cause){
        super(message, cause);
    }

    public SenhaInvalidaException(String message){
        super(message);
    }
    
}
