package com.example.projava.controller;

import com.example.projava.model.TokenRedefinirSenha;
import com.example.projava.service.RedefinirTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/redefinirSenha")
@RestController
public class RedefinirEmailController {


    @Autowired
    RedefinirTokenService redefinirTokenService;

    @PostMapping("/salvar")
    public ResponseEntity<TokenRedefinirSenha>saveToken(@RequestParam String email){
        return  redefinirTokenService.gerarToken(email);
    }


    @PostMapping("/valida")
    public ResponseEntity<TokenRedefinirSenha>validaToken(@RequestParam String token){
        return redefinirTokenService.validarToken(token);
    }

}
