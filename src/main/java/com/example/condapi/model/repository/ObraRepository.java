package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Obra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObraRepository extends JpaRepository<Obra,Long> {
}
