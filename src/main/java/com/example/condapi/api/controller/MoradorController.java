package com.example.condapi.api.controller;

import com.example.condapi.api.dto.EncomendaDTO;
import com.example.condapi.api.dto.MoradorDTO;
import com.example.condapi.model.entity.Encomenda;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.MoradorService;
import com.example.condapi.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/moradores")

public class MoradorController {
    private final MoradorService service;
    private final UnidadeService unidadeService;

    @GetMapping()
    public ResponseEntity get() {
        List<Morador> moradores = service.getMoradores();
        return ResponseEntity.ok(moradores.stream().map(MoradorDTO::create).collect(Collectors.toList()));
    }

    public Morador converter(MoradorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Morador morador = modelMapper.map(dto, Morador.class);
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!unidade.isPresent()) {
                morador.setUnidade(null);
            } else {
                morador.setUnidade(unidade.get());
            }
        }
        return morador;
    }
}
