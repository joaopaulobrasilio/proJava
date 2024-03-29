package com.example.projava.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String nome;
    @NotNull
    private String login;

    @NotNull
    private String email;

    @NotNull
    private String password;



    public UserModel() {

    }

    public UserModel(Integer id, String nome, String login,String email, String password) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.email = email;
        this.password = password;

    }
}
