package com.example.condapi.model.repository;

import com.example.condapi.model.entity.MoradorUnidade;
import com.example.condapi.model.entity.RequisicaoObra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequisicaoObraRepository extends JpaRepository<RequisicaoObra,Long> {
    List<RequisicaoObra> findByMoradorUnidade(Optional<MoradorUnidade> moradorUnidade);
}
