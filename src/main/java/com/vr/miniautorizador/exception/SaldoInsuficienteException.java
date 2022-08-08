package com.vr.miniautorizador.exception;

public class SaldoInsuficienteException extends RuntimeException{
    
    private final static String SALDO_INSUFICIENTE = "SALDO_INSUFICIENTE";

    public SaldoInsuficienteException(){
        super(SALDO_INSUFICIENTE);
    }

    public SaldoInsuficienteException(String message, Throwable cause){
        super(message, cause);
    }

    public SaldoInsuficienteException(String message){
        super(message);
    }
}
