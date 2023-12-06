package com.example.projava.repository;

import com.example.projava.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {
    public Optional<UserModel> findByLogin(String login);

    @Query(value = "SELECT * FROM user_model WHERE email =:email ",nativeQuery = true)
    public Optional<UserModel> findByEmail(String email);


}
