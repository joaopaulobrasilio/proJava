package com.example.projava.controller;

import com.example.projava.Utils.TokenUtils;
import com.example.projava.model.LoginModel;
import com.example.projava.model.RespToken;
import com.example.projava.model.UserModel;
import com.example.projava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private  TokenUtils tokenUtilService;

    @PostMapping("/salvarUsuario")
    public ResponseEntity<UserModel>salvarUsuario(@RequestBody UserModel userModel, @RequestHeader Map<String,String> header) throws Exception {
        //Colocar validação do token
        UserModel newUser = userService.saveUsers(userModel);
       // TokenUtils.validar(header);

        return new  ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
//    @PostMapping("/logar")
//    public ResponseEntity<String> logar(@RequestBody LoginModel login) throws Exception {
//
//        return ResponseEntity.ok("{\"t\":\""+ userService.login(login.getLogin() ,login.getPassword())+"\"}");
//    }
    @PostMapping("/logar")
    public ResponseEntity<RespToken> logar(@RequestBody LoginModel login) throws Exception {
    RespToken  respToken = new RespToken();
    respToken.setToken( userService.fazerLogin(login.getLogin(),login.getPassword()));
        return ResponseEntity.ok(respToken);
    }
    @PostMapping("/validar")
    public ResponseEntity<Boolean> validarAcesso(@RequestHeader String token) throws Exception {
       Boolean ret = tokenUtilService.validarToken(token);
         return ResponseEntity.ok().body(ret);
    }
}
