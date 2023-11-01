package com.example.projava.repository;

import com.example.projava.model.AcessoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcessoRepository extends JpaRepository<AcessoModel, Integer > {


    @Query( value= "Select * from acesso_model order by id asc limit ?1 offset ?2",nativeQuery =true )
    List<AcessoModel> findAllPage(Integer maxItem, Integer offset);
}
