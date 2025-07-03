package com.example.condapi.model.repository;

import com.example.condapi.model.entity.Obra;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.RequisicaoObra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObraRepository extends JpaRepository<Obra,Long> {
    List<Obra> findByPrestadorServico(Optional<PrestadorServico> prestadorServico);

    List<Obra> findByRequisicaoObra(Optional<RequisicaoObra> requisicaoObra);
}
