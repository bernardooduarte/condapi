package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Condominio;
import com.example.condapi.model.entity.Porteiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PorteiroRepository extends JpaRepository<Porteiro,Long> {
    List<Porteiro> findByCondominio(Optional<Condominio> condominio);
}
