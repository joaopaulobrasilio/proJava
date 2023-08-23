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
    private String password;

    @NotNull
    private String roles;

    public UserModel() {

    }

    public UserModel(Integer id, String nome, String login, String password,String roles) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }
}
