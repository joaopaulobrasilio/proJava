package com.example.projava.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;


@ControllerAdvice
public class GlobalControllerAdvice {

    public static final String LOGIN_INEXISTENTE = "login Inexistente";
    public static final String ACESSO_INEXISTENTE = "Acesso Inexistente";
    public static final String TOKEN_EXPIROU_FAÇA_O_LOGIN_NOVAMENTE = "TOKEN EXPIROU FAÇA O LOGIN NOVAMENTE";
    public static final String USUARIO_JA_EXISTENTE = "USUARIO JA EXISTENTE";
    public static final String ERRO_AO_ENVIAR_EMAIL = "Erro ao enviar email";
    public static final String USUÁRIO_NÃO_ENCONTRADO = "Usuário não encontrado";


    private ResponseEntity<StandardError> standardErrorResponseEntity(String error, HttpStatus status, Exception e) {
        StandardError standardError = new StandardError(Instant.now(), status.value(), error);
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(ErrorAoEnviarEmailException.class)
    ResponseEntity<StandardError> errorAoEviarEmailException(ErrorAoEnviarEmailException e) {
        return this.standardErrorResponseEntity(ERRO_AO_ENVIAR_EMAIL, HttpStatus.BAD_REQUEST, e);
    }


    @ExceptionHandler(LoginException.class)
    ResponseEntity<StandardError> loginJaExistente(LoginException e) {
        return this.standardErrorResponseEntity(LOGIN_INEXISTENTE, HttpStatus.NOT_FOUND, e);
    }


    @ExceptionHandler(AcessoNotFoundException.class)
    ResponseEntity<StandardError> acessoNotFound(AcessoNotFoundException e) {
        return this.standardErrorResponseEntity(ACESSO_INEXISTENTE, HttpStatus.NOT_FOUND, e);
    }


    @ExceptionHandler(TokenExpirationException.class)
    ResponseEntity<StandardError> tokenExpiration(TokenExpirationException e) {
        return this.standardErrorResponseEntity(TOKEN_EXPIROU_FAÇA_O_LOGIN_NOVAMENTE, HttpStatus.UNAUTHORIZED, e);
    }


    @ExceptionHandler(UserBadRequestException.class)
    ResponseEntity<StandardError> usuarioExistente(UserBadRequestException e) {
        return standardErrorResponseEntity(USUARIO_JA_EXISTENTE, HttpStatus.BAD_REQUEST, e);

    }


    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<StandardError> usuarioNaoEncontrado(UserNotFoundException e) {
        return this.standardErrorResponseEntity(USUÁRIO_NÃO_ENCONTRADO, HttpStatus.NOT_FOUND, e);
    }


    @ExceptionHandler({UserForbiddenException.class})
    ResponseEntity<StandardError> usuarioSemPermissao(UserForbiddenException e) {
        return this.standardErrorResponseEntity("Usuário sem permissão", HttpStatus.FORBIDDEN, e);

    }


}