package com.example.projava.service;

import com.example.projava.exceptionhandler.UserException;
import com.example.projava.model.UserModel;
import com.example.projava.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenService tokenService;
    public UserService(UserRepository userRepository){
         this.userRepository = userRepository;
    }

    public UserModel salvarUser(UserModel userModel) throws Exception {

             Optional<UserModel> usr = userRepository.verificarLogin(userModel.getLogin());
            if ( usr.isEmpty()) {
                String criptografar = encoder.encode(userModel.getPassword());
                userModel.setPassword(criptografar);
                LOGGER.info("USUARIO CADASTRADO COM SUCESSO");
                return userRepository.save(userModel);

            }else{
                    LOGGER.error("USUARIO JA CADASTRADO USER:{}",userModel);
                    throw new UserException();
                }

            }



       public List<UserModel>findUsersAll(){
        return userRepository.findAll();
       }


       public Optional<UserModel>pegarPorId(Integer id){

        return userRepository.findById(id);
        }

       public ResponseEntity<String> validaSenha(String login , String password){
           Optional<UserModel> optUsuario= userRepository.verificarLogin(login);
           if (optUsuario.isEmpty()) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
           }

           UserModel usuario = optUsuario.get();

           boolean valid = encoder.matches(password, usuario.getPassword());
            if(valid){
                return  ResponseEntity.ok().body( tokenService.gerarToken(usuario));
            }

           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       }




}