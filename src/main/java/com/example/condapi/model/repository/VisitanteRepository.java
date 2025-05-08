package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitanteRepository extends JpaRepository<Visitante,Long> {
}
