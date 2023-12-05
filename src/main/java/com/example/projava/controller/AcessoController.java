package com.example.projava.controller;

import com.example.projava.Utils.TokenUtils;
import exceptionhandler.AcessoNotFoundException;
import com.example.projava.model.AcessoModel;
import com.example.projava.service.AcessoService;
import com.example.projava.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/acessos")
public class AcessoController {
    public static final String ID = "/{id}";


    @Autowired
    private AcessoService acessoService;

    @Autowired
    private  UserService  userService;
    //Construct
    public AcessoController(AcessoService acessoService) {

        this.acessoService = acessoService;

    }

   @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public ResponseEntity<AcessoModel> create(@Valid @RequestBody  AcessoModel acessoModel, @RequestHeader Map<String,String> header ) throws Exception {
       TokenUtils.validarRole(header,"ADMIN");
        AcessoModel newAcesso = acessoService.savar(acessoModel);
        System.out.println(newAcesso);
        return new ResponseEntity<>(newAcesso, HttpStatus.CREATED);
    }

    @RequestMapping(value ="/totalAcessos" ,method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> pegarTotalDeDados(){
       Integer total =  acessoService.findTotal();
        HashMap<String,Integer> map = new HashMap<>();
        map.put("totalDeDados",total);
        return  map;
    }

    //GET
    @RequestMapping( method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AcessoModel> pegarTodos(@RequestHeader Map<String,String> header, @RequestParam Integer pagina, @RequestParam Integer limitePorPagina)
            throws Exception {
        try {
            TokenUtils.validarRole(header,"ADMIN");
            return acessoService.findAll(pagina,limitePorPagina);
        }catch (Exception ex){
             throw new Exception(ex);
        }

    }
    @GetMapping(path = ID)
    public ResponseEntity<AcessoModel> pegarPorId(@PathVariable Integer id) {
        return acessoService.findById(id).map(record -> ResponseEntity.ok().body(record)).orElseThrow(
                () -> new AcessoNotFoundException());

    }

    @DeleteMapping(path = ID)
    public void deletar(@PathVariable Integer id ,@RequestHeader Map<String,String> header) throws Exception {
        TokenUtils.validarRole(header,"ADMIN");
        acessoService.findById(id).map(record -> {
            acessoService.apagar(id);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new AcessoNotFoundException());

    }

    @PutMapping(path = ID)
    public ResponseEntity<AcessoModel> atualizar( @RequestBody AcessoModel model,@PathVariable Integer id ) {
        return acessoService.update(model,id);
    }

}