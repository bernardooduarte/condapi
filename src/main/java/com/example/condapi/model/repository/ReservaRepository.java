package com.example.condapi.model.repository;

import com.example.condapi.model.entity.MoradorUnidade;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    List<Reserva> findByMoradorUnidade(Optional<MoradorUnidade> moradorUnidade);

    List<Reserva> findByPrestadorServico(Optional<PrestadorServico> prestadorServico);
}
