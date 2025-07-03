package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Encomenda;
import com.example.condapi.model.entity.MoradorUnidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EncomendaRepository extends JpaRepository<Encomenda,Long> {
    List<Encomenda> findByMoradorUnidade(Optional<MoradorUnidade> moradorUnidade);
}
