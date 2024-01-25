package com.example.projava.Utils;


import com.example.projava.exception.TokenExpirationException;
import com.example.projava.model.UserModel;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Slf4j
@Service
public class TokenUtils {

    private static final String SECRET_KEY = "batata";

///86400000; // 24 horas em milissegundos
    private static final Integer EXPIRATION_TIME = 60000;

    public String gerarToken(UserModel userModel) {
       // Date now = new Date();
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        return Jwts.builder()
                .setId(String.valueOf(userModel.getId()))
                .setSubject(userModel.getNome())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();

    }

    public boolean validarToken(String token)  {
        try {
            // Parse e verifica a assinatura do token
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

              if(claims.getExpiration().getTime() - System.currentTimeMillis() >= EXPIRATION_TIME){
                  ExpiredJwtException expiredJwtException;
              }
            // Se chegou até aqui, o token é válido
            System.out.println("Token válido");
            return true;

        }catch (ExpiredJwtException exception){
            throw new TokenExpirationException();
        }
        catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            System.out.println("Erro: Token inválido - " + e.getMessage());
        }

        return false;
    }
//    public JWT validaToken(String token) throws Exception { //  função desse método é verificar se as informações do token é valida, não deve conter regras especifica de usuários
//        JWT jwt = mapper.readValue(new String(Base64.getDecoder().decode(token.getBytes())), JWT.class);
//        if (System.currentTimeMillis() - jwt.getTimestamp() >= 60000
//        ) {
//            System.out.println("Expirou");
//            throw new TokenExpirationException();
//        }
////      String[] dToken = new Strin
////      g(Base64.getDecoder().decode(token)).split(":");
////      String login = dToken[0];
////      String password = dToken[1];
//        return jwt;
//    } //extração das informações do token


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
    public boolean validarToken(Map<String, String> header) throws Exception {
        return this.validarToken(header.get("token"));


    }

    public static void validarRole(Map<String, String> header, String role) throws Exception {

        //JWT jwt = new TokenUtils().validarToken(header);
//        if (!jwt.getRoles().stream().collect(Collectors.joining(";")).contains(role)) {
//
//            throw new UserForbiddenException();
//        }
    }

    
    


}
