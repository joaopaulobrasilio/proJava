package com.example.projava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.rmi.server.UID;

@Data
@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;
    @NotNull
    private String nomeCompleto;

    @NotNull

    private String login;

    @NotNull
    private String password;

}
