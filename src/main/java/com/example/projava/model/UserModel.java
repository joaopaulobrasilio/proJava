package com.example.projava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class UserModel  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String nomeCompleto;

    @NotNull

    @Column(length = 20, nullable = false , unique = true)
    private String login;

    @NotNull
    private String password;


}


