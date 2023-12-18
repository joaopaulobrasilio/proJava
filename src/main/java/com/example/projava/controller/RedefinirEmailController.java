package com.example.projava.controller;

import com.example.projava.model.NovaSenha;
import com.example.projava.model.RedefinirSenhaModel;
import com.example.projava.model.TokenRedefinirSenha;
import com.example.projava.service.RedefinirSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/novasenha",method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE )
    public void testaSalvarNovaSenha( @RequestBody NovaSenha novaSenha) throws Exception {
      redefinirTokenService.salvarNovaSenha( novaSenha);

    }

}
