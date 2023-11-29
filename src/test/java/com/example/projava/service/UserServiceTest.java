package com.example.projava.service;

import com.example.projava.model.UserModel;
import com.example.projava.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    UserService userService;


    @Mock
    UserRepository userRepository;

    UserModel user;

    @BeforeEach
     public void setUp(){
     user = new UserModel(1,"joaopaulo", "joaoP","batatinha123","user");
    }
     @Test
     void quando_consultar_login_para_acesso_deve_retornar_um_user(){
         when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(user));

     }


}
