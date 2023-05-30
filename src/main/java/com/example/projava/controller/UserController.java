package com.example.projava.controller;


import com.example.projava.exceptionhandler.AcessoNotFoundException;
import com.example.projava.exceptionhandler.LoginControllerAdvice;
import com.example.projava.exceptionhandler.LoginException;
import com.example.projava.model.AcessoModel;
import com.example.projava.model.UserModel;
import com.example.projava.repository.UserRepository;
import com.example.projava.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder encoder;

      @PostMapping("/salvar")
      public ResponseEntity<UserModel>save(@RequestBody UserModel userModel) throws Exception{
           UserModel newUser = userService.salvarUser(userModel);
           return new ResponseEntity<>(newUser , HttpStatus.CREATED);

      }


    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,
                                                @RequestParam String password) {

        Optional<UserModel> optUsuario= userRepository.findByLogin(login);
        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        UserModel usuario = optUsuario.get();
        boolean valid = encoder.matches(password, usuario.getPassword());

                HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);

    }


   @GetMapping("/{id}")
    public ResponseEntity<UserModel>findById(@PathVariable Integer id){
          return userService.pegarPorId(id).map(record -> ResponseEntity.ok().body(record)
          ).orElseThrow(()-> new LoginException());
    }


}
