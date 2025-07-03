package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.MoradorUnidade;
import com.example.condapi.model.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MoradorUnidadeRepository extends JpaRepository<MoradorUnidade,Long> {
    List<MoradorUnidade> findByUnidade(Optional<Unidade> unidade);

    List<MoradorUnidade> findByMorador(Optional<Morador> morador);
}
