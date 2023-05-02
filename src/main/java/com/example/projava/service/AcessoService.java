package com.example.projava.service;

import com.example.projava.exceptionhandler.AcessoNotFoundException;
import com.example.projava.model.AcessoModel;
import com.example.projava.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AcessoService {

    @Autowired
    AcessoRepository acessoRepository;



    public AcessoModel savar(AcessoModel acessoModel){
        return  acessoRepository.save(acessoModel);
    }

   public List<AcessoModel>findAll(){
        return  acessoRepository.findAll();
   }

   public Optional<AcessoModel> findById ( Integer id){
     return acessoRepository.findById(id);
   }

  public void apagar(Integer id){ acessoRepository.deleteById(id);
  }

  public ResponseEntity<AcessoModel> update(AcessoModel model ,Integer id) {
        return acessoRepository.findById(id).map(
                record ->  {
                    record.setLogin(model.getLogin());
                    record.setSenha(model.getSenha());
                    AcessoModel guard = acessoRepository.save(record);
                   return ResponseEntity.ok().body(guard);
                }).orElseThrow( ()-> new AcessoNotFoundException());
  }

}