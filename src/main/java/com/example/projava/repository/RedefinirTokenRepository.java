package com.example.projava.repository;

import com.example.projava.model.TokenRedefinirSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RedefinirTokenRepository extends JpaRepository<TokenRedefinirSenha,Integer> {

    @Query(value = "SELECT * FROM token_redefinir_senha WHERE token =:token",nativeQuery = true)
    public TokenRedefinirSenha getByToken(String token);




}
