package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondominioRepository extends JpaRepository<Condominio,Long> {
}
