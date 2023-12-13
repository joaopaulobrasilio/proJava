package com.example.projava.controller;

import com.example.projava.model.RedefinirSenhaModel;
import com.example.projava.model.TokenRedefinirSenha;
import com.example.projava.service.RedefinirSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/redefinirsenha")
@RestController
public class RedefinirEmailController {

    @Autowired
    RedefinirSenhaService redefinirTokenService;

    @RequestMapping(value = "/salvar",method = RequestMethod.POST)
    public ResponseEntity<TokenRedefinirSenha>saveToken(@RequestBody RedefinirSenhaModel email) throws Exception {
        return  redefinirTokenService.enviarEmailRecurecaoSenha(email);
    }



    @RequestMapping(value = "/validar",method = RequestMethod.POST)
    public void validaToken(@RequestBody String token){
        redefinirTokenService.validarToken(token);

    }

    @RequestMapping(value = "/novasenha",method = RequestMethod.POST)
    public void testaSalvarNovaSenha(@RequestParam String token, @RequestParam String password ) throws Exception {
       redefinirTokenService.salvarNovaSenha(token, password);

    }

}
