package com.example.projava.repository;

import com.example.projava.model.AcessoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcessoRepository extends JpaRepository<AcessoModel, Integer > {


}
