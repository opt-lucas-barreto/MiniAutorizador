package com.vr.miniautorizador.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vr.miniautorizador.exception.CartaoInexistenteExeception;
import com.vr.miniautorizador.exception.CartaoJaExistenteException;
import com.vr.miniautorizador.exception.MiniAutorizadorException;
import com.vr.miniautorizador.exception.SaldoInsuficienteException;
import com.vr.miniautorizador.exception.SenhaInvalidaException;

@RestControllerAdvice
public class ResourceExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalError> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e, HttpServletRequest request
    ){
        GlobalError globalError = new GlobalError(e.getFieldError().getField()+"::"+e.getFieldError().getDefaultMessage(), e.getFieldError().getCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(globalError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalError> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException e, HttpServletRequest request
    ){
        GlobalError globalError = new GlobalError(e.getMessage(), e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(globalError);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GlobalError> handleRuntimeException(
        HttpMessageNotReadableException e, HttpServletRequest request
    ){
        GlobalError globalError = new GlobalError(e.getMessage(), e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(globalError);
    }

    @ExceptionHandler(MiniAutorizadorException.class)
    public ResponseEntity<GlobalError> handleMiniAutorizadorException(
        HttpMessageNotReadableException e, HttpServletRequest request
    ){
        GlobalError globalError = new GlobalError(e.getMessage(), e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(globalError);
    }

    @ExceptionHandler(CartaoInexistenteExeception.class)
    public ResponseEntity<GlobalError> handleCartaoInexistenteExeception(
        HttpMessageNotReadableException e, HttpServletRequest request
    ){
        GlobalError globalError = new GlobalError(e.getMessage(), e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(globalError);
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<GlobalError> handleSenhaInvalidaException(
        HttpMessageNotReadableException e, HttpServletRequest request
    ){
        GlobalError globalError = new GlobalError(e.getMessage(), e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(globalError);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<GlobalError> handleSaldoInsuficienteException(
        HttpMessageNotReadableException e, HttpServletRequest request
    ){
        GlobalError globalError = new GlobalError(e.getMessage(), e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(globalError);
    }

}