package com.example.projava.exceptionhandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice
public class AcessoControllerAdvice {

    public static final String ACESSO_NAO_ENCONTRADO = "Acesso não encontrado";

    @ResponseBody
    @ExceptionHandler({AcessoNotFoundException.class})
    ResponseEntity<MessageExceptionHandler> acessoNotFound(AcessoNotFoundException acessoNotFoundException) {
        MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), ACESSO_NAO_ENCONTRADO);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
