package com.example.condapi.api.controller;

import com.example.condapi.api.dto.UnidadeDTO;
import com.example.condapi.model.entity.Bloco;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/unidades")

public class UnidadeController {

    private final UnidadeService service;
    private final BlocoService blocoService;

    public Unidade converter(UnidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Unidade unidade = modelMapper.map(dto, Unidade.class);
        if (dto.getIdBloco() != null) {
            Optional<Bloco> bloco = blocoService.getBlocoById(dto.getIdBloco());
            if (!bloco.isPresent()) {
                unidade.setBloco(null);
            } else {
               unidade.setBloco(bloco.get());
            }
        }
        return unidade;
    }
}
