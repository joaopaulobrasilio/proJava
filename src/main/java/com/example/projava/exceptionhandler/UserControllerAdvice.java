package com.example.projava.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice
public class UserControllerAdvice {


    public static final String USUARIO_JA_EXISTENTE = "USUARIO JA EXISTENTE";

    @ResponseBody
    @ExceptionHandler({UserException.class})
    ResponseEntity<MessageExceptionHandler> usuarioExistente(UserException userBadRequestException){
        MessageExceptionHandler error = new MessageExceptionHandler(
                new Date(), HttpStatus.BAD_REQUEST.value(), USUARIO_JA_EXISTENTE);
        return  new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }


}
