package com.example.projava.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenRedefinirSenha {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer id;

   @NotNull
    private  Integer id_user;

   @NotNull
    private String  token;


   @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long timestamp;




}
