package com.example.projava.service;

import exceptionhandler.AcessoNotFoundException;
import com.example.projava.model.AcessoModel;
import com.example.projava.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AcessoService {

    @Autowired
    AcessoRepository acessoRepository;

    @Autowired
    RedefinirSenhaService redefinirTokenService;

    Collection<AcessoModel> list = new ArrayList<>();

    public AcessoModel savar(AcessoModel acessoModel) {
        acessoModel.setDataRegistro(Calendar.getInstance());

//       ACHAR OUTRO METÓDOTO DE ENCRIPTAR
//        BCryptPasswordEncoder encriptarAcesso = new BCryptPasswordEncoder();
//        String encript = encriptarAcesso.encode(acessoModel.getSenha());
//        acessoModel.setSenha(encript);
        return acessoRepository.save(acessoModel);
    }

    public List<AcessoModel> findAll(Integer pagina, Integer limitePorPagina) throws AcessoNotFoundException {

        return acessoRepository.findAllPage(limitePorPagina, ((pagina) * limitePorPagina));
    }

    public Integer findTotal() {
        return acessoRepository.findTotal();
    }


    public Optional<AcessoModel> findById(Integer id) {
        return acessoRepository.findById(id);
    }

    public void apagar(Integer id) {
        acessoRepository.deleteById(id);
    }


    public ResponseEntity<AcessoModel> update(AcessoModel model, Integer id) {
        return acessoRepository.findById(id).map(
                record -> {
                    record.setLogin(model.getLogin());
                    record.setSenha(model.getSenha());
                    record.setDescricao(model.getDescricao());
                    AcessoModel guard = acessoRepository.save(record);
                    return ResponseEntity.ok().body(guard);
                }).orElseThrow(() -> new AcessoNotFoundException());
    }

}