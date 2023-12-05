package com.example.projava.Utils;

import exceptionhandler.TokenExpirationException;
import exceptionhandler.UserForbiddenException;
import com.example.projava.model.JWT;
import com.example.projava.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class TokenUtils {


    private static final ObjectMapper mapper = new ObjectMapper();


    public String gerarToken(UserModel userModel, String... Roles) {

        try {
            String json = mapper.writeValueAsString(JWT.builder().username(userModel.getLogin()).password(userModel.getPassword()).roles(List.of(Roles)).timestamp(System.currentTimeMillis()).build());
            return new String(Base64.getEncoder().encode(json.getBytes()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //    return  new String(Base64.getEncoder().encode(userModel.getLogin().concat(":").concat(userModel.getPassword()).getBytes()));
    } //gerarToken

    public JWT validarToken(String token) throws Exception { //  função desse método é verificar se as informações do token é valida, não deve conter regras especifica de usuários
        JWT jwt = mapper.readValue(new String(Base64.getDecoder().decode(token.getBytes())), JWT.class);
        if (System.currentTimeMillis() - jwt.getTimestamp() >= 86400000
        ) {
            System.out.println("Expirou");
            throw new TokenExpirationException();
        } else {
            System.out.println("TOKEN VALIDO");
        }
//      String[] dToken = new Strin
//      g(Base64.getDecoder().decode(token)).split(":");
//      String login = dToken[0];
//      String password = dToken[1];
        return jwt;
    } //extração das informações do token


    //  public  static void validar(Map<String,String> header) throws Exception {
//
//       try{
//
//           new TokenUtils().validarToken(header);
//
//       }catch (TokenExpirationException exception) {
//           throw new TokenExpirationException();
//       }
//    }
    public JWT validarToken(Map<String, String> header) throws Exception {
        return this.validarToken(header.get("token"));


    }

    public static void validarRole(Map<String, String> header, String role) throws Exception {

        JWT jwt = new TokenUtils().validarToken(header);
        if (!jwt.getRoles().stream().collect(Collectors.joining(";")).contains(role)) {

            throw new UserForbiddenException();
        }
    }


}
