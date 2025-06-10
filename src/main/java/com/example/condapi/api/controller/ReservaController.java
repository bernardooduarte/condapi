package com.example.condapi.api.controller;



import com.example.condapi.api.dto.MoradorDTO;
import com.example.condapi.api.dto.ReservaDTO;
import com.example.condapi.api.dto.UnidadeDTO;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.Reserva;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.MoradorService;
import com.example.condapi.service.ReservaService;
import com.example.condapi.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/reservas")

public class ReservaController {
    private final ReservaService service;
    private final MoradorService moradorService;
    private final UnidadeService unidadeService;

    @GetMapping()
    public ResponseEntity get() {
        List<Reserva> reservas = service.getReservas();
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity("reserva não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reserva.map(ReservaDTO::create));
    }

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
                reserva.setUnidade(unidade.get());
            }
        }
        return reserva;
    }
}
