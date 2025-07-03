package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Condominio;
import com.example.condapi.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Long> {
    List<Funcionario> findByCondominio(Optional<Condominio> condominio);
}
