package com.example.projava.service;


import com.example.projava.Utils.TokenUtils;
import com.example.projava.exception.UserNotFoundException;
import com.example.projava.model.UserModel;
import com.example.projava.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private  TokenUtils tokenUtils =  new TokenUtils();

    public UserModel saveUsers(UserModel userModel){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encript = encoder.encode(userModel.getPassword());
        userModel.setPassword(encript);
       return userRepository.save(userModel);

    }

    public String fazerLogin(String login, String password) throws Exception {

       UserModel userAuth = this.autenticate(login,password);
        if(userAuth != null){
        String jwtToken= tokenUtils.gerarToken(userAuth);
        return  jwtToken;
        }
        else {
            throw new Exception();
        }
    }

    public UserModel autenticate(String login ,String password) throws Exception {
      Optional<UserModel> model =  userRepository.findByLogin(login);
      if(model.isEmpty()){
          throw new UserNotFoundException(HttpStatus.NOT_FOUND.toString());
      }
      if(!BCrypt.checkpw(password,model.get().getPassword())){
          throw new UserNotFoundException(HttpStatus.FORBIDDEN.toString());
      }

         return model.orElse(null);

    }


}
