package com.example.projava.controller;

import com.example.projava.model.AcessoModel;
import com.example.projava.repository.AcessoRepository;
import com.example.projava.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acessos")
public class AcessoController {
    @Autowired
    private AcessoService acessoService;
    private final AcessoRepository acessoRepository;

    //Construct
    public AcessoController(AcessoService acessoService,
                            AcessoRepository acessoRepository) {

        this.acessoService = acessoService;
        this.acessoRepository = acessoRepository;
    }


    @PostMapping("/salvar")
    public AcessoModel create(@RequestBody AcessoModel acessoModel) {

        return acessoService.savar(acessoModel);
    }

    @GetMapping
    public List<AcessoModel> pegarTodos() {

        return acessoService.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AcessoModel> pegarPorId(@PathVariable Integer id) {
        return acessoService.findById(id).map(record ->
                ResponseEntity.ok().body(record)).orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        acessoService.findById(id).map(record -> {
            acessoService.apagar(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());

    }
    @PutMapping("/{id}")
    public ResponseEntity<AcessoModel> atualizar(@PathVariable Integer id , @RequestBody AcessoModel model){
        return acessoService.findById(id).map( record ->{
            record.setLogin(model.getLogin());
            record.setSenha(model.getSenha());
           AcessoModel guard = acessoService.savar(record);
           return ResponseEntity.ok().body(guard);
        }).orElse(ResponseEntity.notFound().build());
    }
}