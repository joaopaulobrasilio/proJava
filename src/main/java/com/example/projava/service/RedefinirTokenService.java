package com.example.projava.service;


import com.example.projava.Utils.Formatar;
import com.example.projava.model.TokenRedefinirSenha;
import com.example.projava.model.UserModel;
import com.example.projava.repository.RedefinirTokenRepository;
import com.example.projava.repository.UserRepository;
import exceptionhandler.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class RedefinirTokenService {


    @Autowired
    private RedefinirTokenRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Formatar formatar;

    TokenRedefinirSenha tokenRedefinirSenha = new TokenRedefinirSenha();



    public ResponseEntity<TokenRedefinirSenha> gerarToken(String email) {
        UUID token = UUID.randomUUID();
        String myToken = token.toString();
        Optional<UserModel> resp = userRepository.findByEmail(email);
        if(email != resp.get().getEmail()){
              throw new UserNotFoundException("ERROR");
        }

        TokenRedefinirSenha dados = formatar.formatarDados(resp.get().getId(),
                myToken, System.currentTimeMillis());
        repository.save(dados);
        return ResponseEntity.ok().build();
    }


    public ResponseEntity<TokenRedefinirSenha> validarToken(String token) {
        TokenRedefinirSenha tk = repository.verifyExpirationToken(token);
        if( System.currentTimeMillis() - tk.getTimestamp() >= 2000 ){
            System.out.println("CAIU AQUI");
        }
          TokenRedefinirSenha retornoToken = formatar.tokenRetornado(tk.getToken());
      return ResponseEntity.ok().body(retornoToken);

    }




}



