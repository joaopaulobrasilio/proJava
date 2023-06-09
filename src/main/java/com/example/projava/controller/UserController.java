package com.example.projava.controller;


import com.example.projava.exceptionhandler.LoginException;
import com.example.projava.model.LoginModel;
import com.example.projava.model.UserModel;
import com.example.projava.repository.UserRepository;
import com.example.projava.service.TokenService;
import com.example.projava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private TokenService tokenService;

   @Autowired
   private AuthenticationManager authenticationManager;

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


    @PostMapping("/validarSenha")
    public ResponseEntity<String> validarSenha(@RequestBody LoginModel login) {

          return  userService.validaSenha(login.getLogin(),login.getPassword());


    }

    @GetMapping
    public List<UserModel>listarTodos(){
          return userService.findUsersAll();
    }



   @GetMapping("/{id}")
    public ResponseEntity<UserModel>findById(@PathVariable Integer id){
          return userService.pegarPorId(id).map(record -> ResponseEntity.ok().body(record)
          ).orElseThrow(()-> new LoginException());
    }
//
//@PostMapping("/login")
//    public  String login(@RequestBody Login login){
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                new UsernamePasswordAuthenticationToken(login.login(),login.password());
//
//        Authentication authentication =
//        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        var usuario = (UserModel)  authentication.getPrincipal();
//
//        return tokenService.gerarToken(usuario);
//    }
}
