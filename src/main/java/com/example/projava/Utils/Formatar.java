package com.example.projava.Utils;

import com.example.projava.model.TokenRedefinirSenha;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Formatar {


    public TokenRedefinirSenha formatarDados(Integer id_user, String token, Long timestamp) {
        TokenRedefinirSenha tokenRedefinirSenha = new TokenRedefinirSenha();
        tokenRedefinirSenha.setId_user(id_user);
        tokenRedefinirSenha.setToken(token);
        tokenRedefinirSenha.setTimestamp(timestamp);

        return tokenRedefinirSenha;
    }


    public TokenRedefinirSenha tokenRetornado(String token) {
        TokenRedefinirSenha tokenRedefinirSenha = new TokenRedefinirSenha();
        tokenRedefinirSenha.setToken(token);
        return tokenRedefinirSenha;
    }

}
