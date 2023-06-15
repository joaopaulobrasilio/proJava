package com.example.projava.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.projava.model.UserModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public String gerarToken(UserModel userModel){

        return JWT.create()
                .withSubject(userModel.getLogin())
                .withIssuer("Acessos")
                .withClaim("id", userModel.getId())
                .withExpiresAt(LocalDateTime.now()
                        //.plusMinutes(10)
                        .plusSeconds(50)
                        .toInstant(ZoneOffset.of("-03:00"))).sign(
                                Algorithm.HMAC256("secret")
                );

    }

     public String getSubject(String token){
        return JWT.require(Algorithm.HMAC256("secret"))
                .withIssuer("Acessos")
                .build().verify(token).getSubject();
     }


}
