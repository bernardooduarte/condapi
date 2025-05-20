package com.example.condapi.api.controller;

import com.example.condapi.api.dto.BlocoDTO;
import com.example.condapi.api.dto.EncomendaDTO;
import com.example.condapi.model.entity.*;
import com.example.condapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EncomendaController {


    private final EncomendaService service;
    private final MoradorService moradorService;
    private final UnidadeService unidadeService;
    private final PorteiroService porteiroService;

    public Encomenda converter(EncomendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Encomenda encomenda = modelMapper.map(dto, Encomenda.class);
        if (dto.getIdMorador() != null) {
            Optional<Morador> morador = moradorService.getMoradorById(dto.getIdMorador());
            if (!morador.isPresent()) {
                encomenda.setMorador(null);
            } else {
                encomenda.setMorador(morador.get());
            }
        }
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!morador.isPresent()) {
                encomenda.setMorador(null);
            } else {
                encomenda.setMorador(morador.get());
            }
        }
        return encomenda;
    }
}
