package exceptionhandler;


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




    private ResponseEntity<StandardError> standardErrorResponseEntity(String error, HttpStatus status, Exception e){
        StandardError standardError = new StandardError(Instant.now(),status.value(),error, e.getMessage());
        return ResponseEntity.status(status).body(standardError);
    }
    @ExceptionHandler(ErrorAoEnviarEmailException.class)
    ResponseEntity<StandardError> errorEmailException(ErrorAoEnviarEmailException e){
        return this.standardErrorResponseEntity("Erro ao enviar email",HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<StandardError> exception(Exception e){
        return this.standardErrorResponseEntity("Erro ao enviar email",HttpStatus.BAD_REQUEST, e);
    }





//   @ExceptionHandler({LoginException.class})
//    ResponseEntity<StandardError> loginJaExistente(LoginException loginBadRequestException){
//        StandardError error = new StandardError(
//                new Date(), HttpStatus.BAD_REQUEST.value(), LOGIN_INEXISTENTE);
//        return  new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
//
//    }
//
//
//
//    @ExceptionHandler({AcessoNotFoundException.class})
//    ResponseEntity<StandardError> acessoNotFound(AcessoNotFoundException acessoNotFoundException) {
//        StandardError error = new StandardError(new Date(), HttpStatus.NOT_FOUND.value(),
//                ACESSO_INEXISTENTE);
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }
//
//
//
//    @ExceptionHandler({TokenExpirationException.class})
//    ResponseEntity<StandardError> tokenExpiration(TokenExpirationException tokenExpirationException) {
//        StandardError error = new StandardError(new Date(), HttpStatus.UNAUTHORIZED.value(), TOKEN_EXPIROU_FAÇA_O_LOGIN_NOVAMENTE);
//        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
//    }
//
//
//
//    @ExceptionHandler({UserBadRequestException.class})
//    ResponseEntity<StandardError> usuarioExistente(UserBadRequestException userBadRequestException){
//        StandardError error = new StandardError(
//                new Date(), HttpStatus.BAD_REQUEST.value(), USUARIO_JA_EXISTENTE);
//        return  new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
//
//    }
//
//
//    @ExceptionHandler({UserNotFoundException.class})
//    ResponseEntity<StandardError> usuarioNaoEncontrado(UserNotFoundException userNotFoundException){
//        StandardError error = new StandardError(
//                new Date(), HttpStatus.NOT_FOUND.value(), "Usuário não encontrado");
//        return  new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
//
//    }
//
//
//    @ExceptionHandler({UserForbiddenException.class})
//    ResponseEntity<StandardError> usuarioSemPermissao(UserForbiddenException userForbiddenException){
//        StandardError error = new StandardError(
//                new Date(), HttpStatus.FORBIDDEN.value(), "Usuário sem permissão");
//        return  new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
//
//    }
//
//
//




}