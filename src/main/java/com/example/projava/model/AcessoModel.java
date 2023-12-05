package com.example.projava.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

import static jakarta.persistence.TemporalType.*;


@Data
@Entity
public class AcessoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String login;
    @NotNull
    private String senha;
    @NotNull
    private String descricao;

    @Temporal(DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Calendar dataRegistro;

    public AcessoModel() {
    }

    public AcessoModel(Integer id, String login, String senha, String descricao, Date dataRegistro) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.descricao = descricao;
        setDataRegistro(Calendar.getInstance());
    }
}
