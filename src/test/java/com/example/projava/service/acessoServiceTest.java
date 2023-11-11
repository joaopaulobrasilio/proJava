package com.example.projava.service;


import com.example.projava.model.AcessoModel;
import com.example.projava.repository.AcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class acessoServiceTest {


    @InjectMocks
   AcessoService acessoService;


    @Mock
   AcessoRepository acessoRepository;

    AcessoModel acesso;

    @BeforeEach
    public void setUp(){

        acesso = new AcessoModel(1, "rango", "batata","test", new Date());
    }

    @Test
    void  quando_buscar_por_id_deve_retornar_um_acesso(){
        when(acessoRepository.findById(acesso.getId())).thenReturn(Optional.of(acesso));
      Optional<AcessoModel> acess = acessoService.findById(acesso.getId());
      assertEquals(Optional.ofNullable(acesso), acess);

    }

    @Test
    void  quando_apagar_deve_dar_sucesso(){
     acessoService.apagar(acesso.getId());
     verify(acessoRepository).deleteById(acesso.getId());
    }



}

