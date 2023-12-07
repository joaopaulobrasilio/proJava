package com.example.projava.controller;

import com.example.projava.model.TokenRedefinirSenha;
import com.example.projava.service.RedefinirSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/redefinirSenha")
@RestController
public class RedefinirEmailController {


    @Autowired
    RedefinirSenhaService redefinirTokenService;

    @RequestMapping(value = "/salvar",method = RequestMethod.POST)
    public ResponseEntity<TokenRedefinirSenha>saveToken(@RequestParam String email) throws Exception {
        return  redefinirTokenService.enviarEmailRecurecaoSenha(email);
    }



    @RequestMapping(value = "/valida",method = RequestMethod.POST)
    public void validaToken(@RequestParam String token){
        redefinirTokenService.validarToken(token);

    }
}
