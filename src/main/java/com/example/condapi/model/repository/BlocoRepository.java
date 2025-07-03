package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Bloco;
import com.example.condapi.model.entity.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlocoRepository extends JpaRepository<Bloco,Long> {
    List<Bloco> findByCondominio(Optional<Condominio> condominio);
}
