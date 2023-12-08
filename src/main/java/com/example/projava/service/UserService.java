package com.example.projava.service;


import com.example.projava.Utils.TokenUtils;
import com.example.projava.exception.UserNotFoundException;
import com.example.projava.model.LoginModel;
import com.example.projava.model.UserModel;
import com.example.projava.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private  UserRepository userRepository;


    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();



    public UserModel saveUsers(UserModel userModel){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encript = encoder.encode(userModel.getPassword());
        userModel.setPassword(encript);
       return userRepository.save(userModel);

    }

    public String fazerlogin(LoginModel login) throws Exception {
      Optional<UserModel> model =  userRepository.findByLogin(login.getLogin());
      if(model.isEmpty()){
          throw new UserNotFoundException(HttpStatus.NOT_FOUND.toString());
      }
      if(!BCrypt.checkpw(login.getPassword(),model.get().getPassword())){
          throw new UserNotFoundException(HttpStatus.FORBIDDEN.toString());
      }
        //return new String(Base64.getEncoder().encode(login.concat(":").concat(password).getBytes()));
         return  new TokenUtils().gerarToken(model.get(),model.get().getRoles());

    }
    public ResponseEntity<String> validarAutenticacaoUsers(Map<String,String> token) throws Exception {
        TokenUtils tokenUtis = new TokenUtils();
        tokenUtis.validarToken(token);
            return ResponseEntity.ok().build();
        }



}
