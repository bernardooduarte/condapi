package com.example.condapi.model.repository;

import com.example.condapi.model.entity.MoradorUnidade;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.model.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {
    List<Veiculo> findByMoradorUnidade(Optional<MoradorUnidade> moradorUnidade);
}
