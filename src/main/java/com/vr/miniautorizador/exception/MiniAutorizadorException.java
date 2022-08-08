package com.vr.miniautorizador.exception;

public class MiniAutorizadorException extends RuntimeException{
    
    private final static String ERRO_NO_SISTEMA = "Algo errado no Sistema";

    public MiniAutorizadorException(){
        super(ERRO_NO_SISTEMA);
    }

    public MiniAutorizadorException(String message, Throwable cause){
        super(message, cause);
    }

    public MiniAutorizadorException(String message){
        super(message);
    }

}
