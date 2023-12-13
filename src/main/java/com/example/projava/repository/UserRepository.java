package com.example.projava.repository;

import com.example.projava.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {
     Optional<UserModel> findByLogin(String login);

    @Query(value = "SELECT * FROM user_model WHERE email =:email ",nativeQuery = true)
      Optional<UserModel> findByEmail(String email);


    @Query(value = "UPDATE  user_model set password =:password WHERE  id =:id",nativeQuery = true)
     Optional<Long> atualizarSenha(String password,Integer id);


}
