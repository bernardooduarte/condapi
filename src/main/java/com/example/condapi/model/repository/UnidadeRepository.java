package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Bloco;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.model.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnidadeRepository extends JpaRepository<Unidade,Long> {
}
