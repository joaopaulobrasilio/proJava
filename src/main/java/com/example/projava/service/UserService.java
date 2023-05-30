package com.example.projava.service;

import com.example.projava.model.UserModel;
import com.example.projava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    public UserService(UserRepository userRepository){
         this.userRepository = userRepository;
    }

    public UserModel salvarUser(UserModel userModel) throws  Exception{


              String criptografar = encoder.encode(userModel.getPassword());
              String s =  userModel.getLogin();

             if(s.isEmpty()){
                 userModel.setPassword(criptografar);
                 return  userRepository.save(userModel);
             }else{
                   throw new RuntimeException();
             }

          }
       public Optional<UserModel>pegarPorId(Integer id){

        return userRepository.findById(id);
       }

}