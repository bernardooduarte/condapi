package com.example.condapi.api.controller;



import com.example.condapi.api.dto.ReservaDTO;
import com.example.condapi.model.entity.Reserva;
import com.example.condapi.service.MoradorService;
import com.example.condapi.service.ReservaService;
import com.example.condapi.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor

public class ReservaController {
    private final ReservaService service;
    private final MoradorService moradorService;
    private final UnidadeService unidadeService;

    public Reserva converter(ReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Reserva reserva = modelMapper.map(dto, Reserva.class);
        if (dto.getIdMorador() != null) {
            Optional<Morador> morador = moradorService.getMoradorById(dto.getIdMorador());
            if (!morador.isPresent()) {
                reserva.setMorador(null);
            } else {
                reserva.setMorador(morador.get());
            }
        }
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!unidade.isPresent()) {
                reserva.setUnidade(null);
            } else {
                reserva.setUnidade(Unidade.get());
            }
        }
        return reserva;
    }
}
