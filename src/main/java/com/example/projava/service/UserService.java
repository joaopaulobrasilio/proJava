package com.example.projava.service;

import com.example.projava.exceptionhandler.AcessoNotFoundException;
import com.example.projava.model.UserModel;
import com.example.projava.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;


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
                    throw new AcessoNotFoundException();
                }

            }



       public Optional<UserModel>pegarPorId(Integer id){

        return userRepository.findById(id);
        }

//       public ResponseEntity<Boolean> validaSenha(String login , String password){
//           UserModel userModel;
//           Optional<UserModel> optUsuario= userRepository.findByLogin();
//           if (optUsuario.isEmpty()) {
//               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
//           }
//
//           UserModel usuario = optUsuario.get();
//
//           boolean valid = encoder.matches(password, usuario.getPassword());
//           HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
//           return ResponseEntity.status(status).body(valid);
//       }




}