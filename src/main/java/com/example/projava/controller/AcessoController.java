package com.example.projava.controller;

import com.example.projava.model.AcessoModel;
import com.example.projava.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AcessoController {

    @Autowired
    private final AcessoRepository repository;


    //Construct
    public AcessoController(AcessoRepository repository) {
       this.repository=  repository;
    }


     @PostMapping("/salvar")
     public AcessoModel create(@RequestBody AcessoModel acessoModel, HttpStatus status){

        return repository.save(acessoModel);
     }

}
