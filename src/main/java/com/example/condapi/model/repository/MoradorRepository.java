package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Morador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoradorRepository extends JpaRepository<Morador,Long> {
}
