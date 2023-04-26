package com.example.projava.controller;

import com.example.projava.model.AcessoModel;
import com.example.projava.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acessos")
public class AcessoController {
    @Autowired
     private AcessoService acessoService;

    //Construct
    public AcessoController(AcessoService acessoService) {

        this.acessoService=  acessoService;
    }


     @PostMapping("/salvar")
     public AcessoModel create(@RequestBody AcessoModel acessoModel){
       return acessoService.savar(acessoModel);
     }

     @GetMapping
     public List<AcessoModel> pegarTodos(){
        return  acessoService.findAll();
     }
     @GetMapping(path = "/{id}")
     public ResponseEntity<AcessoModel> pegarPorId(@PathVariable Integer id ){
        return acessoService.findById(id).map( record ->
            ResponseEntity.ok().body(record)).orElse(ResponseEntity.notFound().build());

     }


}
