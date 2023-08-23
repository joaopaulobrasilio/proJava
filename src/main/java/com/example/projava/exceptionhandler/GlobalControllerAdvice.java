package com.example.projava.exceptionhandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;



@ControllerAdvice
public class GlobalControllerAdvice {

    public static final String LOGIN_INEXISTENTE = "login Inexistente";
    public static final String ACESSO_INEXISTENTE = "Acesso Inexistente";
    public static final String TOKEN_EXPIROU_FAÇA_O_LOGIN_NOVAMENTE = "TOKEN EXPIROU FAÇA O LOGIN NOVAMENTE";
    public static final String USUARIO_JA_EXISTENTE = "USUARIO JA EXISTENTE";

    @ResponseBody
    @ExceptionHandler({LoginException.class})
    ResponseEntity<MessageExceptionHandler> loginJaExistente(LoginException loginBadRequestException){
        MessageExceptionHandler error = new MessageExceptionHandler(
                new Date(), HttpStatus.BAD_REQUEST.value(), LOGIN_INEXISTENTE);
        return  new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }


    @ResponseBody
    @ExceptionHandler({AcessoNotFoundException.class})
    ResponseEntity<MessageExceptionHandler> acessoNotFound(AcessoNotFoundException acessoNotFoundException) {
        MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(),
                ACESSO_INEXISTENTE);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ResponseBody
    @ExceptionHandler({TokenExpirationException.class})
    ResponseEntity<MessageExceptionHandler> tokenExpiration(TokenExpirationException tokenExpirationException) {
        MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.UNAUTHORIZED.value(), TOKEN_EXPIROU_FAÇA_O_LOGIN_NOVAMENTE);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @ResponseBody
    @ExceptionHandler({UserBadRequestException.class})
    ResponseEntity<MessageExceptionHandler> usuarioExistente(UserBadRequestException userBadRequestException){
        MessageExceptionHandler error = new MessageExceptionHandler(
                new Date(), HttpStatus.BAD_REQUEST.value(), USUARIO_JA_EXISTENTE);
        return  new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }

    @ResponseBody
    @ExceptionHandler({UserNotFoundException.class})
    ResponseEntity<MessageExceptionHandler> usuarioNaoEncontrado(UserNotFoundException userNotFoundException){
        MessageExceptionHandler error = new MessageExceptionHandler(
                new Date(), HttpStatus.NOT_FOUND.value(), "Usuário não encontrado");
        return  new ResponseEntity<>(error,HttpStatus.NOT_FOUND);

    }

    @ResponseBody
    @ExceptionHandler({UserForbiddenException.class})
    ResponseEntity<MessageExceptionHandler> usuarioSemPermissao(UserForbiddenException userNotFoundException){
        MessageExceptionHandler error = new MessageExceptionHandler(
                new Date(), HttpStatus.FORBIDDEN.value(), "Usuário sem permissão");
        return  new ResponseEntity<>(error,HttpStatus.FORBIDDEN);

    }


}