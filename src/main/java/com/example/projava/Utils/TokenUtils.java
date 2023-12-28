package com.example.projava.Utils;

import com.example.projava.exception.TokenExpirationException;
import com.example.projava.exception.UserForbiddenException;
import com.example.projava.model.JWT;
import com.example.projava.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
public class TokenUtils {

    private static final String SECRET_KEY = "xibiu";
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Integer EXPIRATION_TIME = 86400000; // 24 horas em milissegundos

    public String gerarToken(UserModel userModel) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .setId(String.valueOf(userModel.getId()))
                .setSubject(userModel.getRoles())
                .setSubject(userModel.getNome())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();

    }
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean validarTokeen(String token ,UserModel userModel) {
        try {
            // Parse e verifica a assinatura do token
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            // Você pode adicionar verificações adicionais aqui, se necessário
            // Exemplo: Verificar roles, expiração, issuer, etc.
           final  String username = getUsernameFromToken(token);
           if(!username.equals(userModel.getNome())&& !isTokenExpired(token)){
               throw  new TokenExpirationException();
           }

            // Se chegou até aqui, o token é válido
            return true;
        } catch (TokenExpirationException|ExpiredJwtException e) {
            System.out.println("Erro: Token expirado.");
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            System.out.println("Erro: Token inválido - " + e.getMessage());
        }

        // Se houver qualquer exceção, o token é considerado inválido
        return false;
    }
    public JWT validarToken(String token) throws Exception { //  função desse método é verificar se as informações do token é valida, não deve conter regras especifica de usuários
        JWT jwt = mapper.readValue(new String(Base64.getDecoder().decode(token.getBytes())), JWT.class);
        if (System.currentTimeMillis() - jwt.getTimestamp() >= 86400000
        ) {
            System.out.println("Expirou");
            throw new TokenExpirationException();
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
