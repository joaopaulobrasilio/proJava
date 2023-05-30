package com.example.projava.repository;

import com.example.projava.model.UserModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserModel , Integer> {

   Optional<UserModel> findByLogin(String login);



}
