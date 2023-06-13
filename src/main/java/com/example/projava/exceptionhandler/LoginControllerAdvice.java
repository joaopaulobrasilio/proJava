package com.example.projava.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
@ControllerAdvice
public class LoginControllerAdvice {

//    public static final String LOGIN_NAO_ENCONTRADO = "Login não encontrado";
    public static final String LOGIN_JÁ_EXISTENTE = "LOGIN JÁ EXISTENTE";

//    @ResponseBody
//    @ExceptionHandler({LoginException.class})
//    ResponseEntity<MessageExceptionHandler> loginNotFound(LoginException loginNotFoundException) {
//        MessageExceptionHandler error = new MessageExceptionHandler(
//                new Date(), HttpStatus.NOT_FOUND.value(), LOGIN_NAO_ENCONTRADO);
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }


    @ResponseBody
        @ExceptionHandler({LoginException.class})
    ResponseEntity<MessageExceptionHandler>loginJaExistente(LoginException loginBadRequestException){
        MessageExceptionHandler error = new MessageExceptionHandler(
                new Date(),HttpStatus.BAD_REQUEST.value(), LOGIN_JÁ_EXISTENTE);
        return  new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }

}
