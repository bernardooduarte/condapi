package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Encomenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncomendaRepository extends JpaRepository<Encomenda,Long> {
}
