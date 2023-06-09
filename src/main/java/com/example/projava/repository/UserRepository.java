package com.example.projava.repository;

import com.example.projava.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<UserModel , Integer> {

   UserDetails findByLogin(String login);

  @Query("from UserModel t where t.login = :login")
   Optional<UserModel>verificarLogin(String login);


}
