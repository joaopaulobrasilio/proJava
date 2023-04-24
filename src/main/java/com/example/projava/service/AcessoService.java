package com.example.projava.service;

import com.example.projava.model.AcessoModel;
import com.example.projava.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AcessoService {

    @Autowired
    AcessoRepository acessoRepository;

    public void save(AcessoModel acessoModel){
        acessoRepository.save(acessoModel);
   }
}