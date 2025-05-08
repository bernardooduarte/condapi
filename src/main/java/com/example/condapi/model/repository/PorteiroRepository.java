package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Porteiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PorteiroRepository extends JpaRepository<Porteiro,Long> {
}
