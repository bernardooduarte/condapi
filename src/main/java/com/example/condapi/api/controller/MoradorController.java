package com.example.condapi.api.controller;

import com.example.condapi.api.dto.MoradorDTO;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.MoradorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/moradores")

public class MoradorController {
    private final MoradorService service;

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
