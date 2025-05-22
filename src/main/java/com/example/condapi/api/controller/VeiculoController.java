package com.example.condapi.api.controller;


import com.example.condapi.service.MoradorService;
import com.example.condapi.service.UnidadeService;
import com.example.condapi.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/veiculos")


public class VeiculoController {
    private final VeiculoService service;
    private final MoradorService moradorService;
    private final UnidadeService unidadeService;

    public Veiculo converter(VeiculoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);
        if (dto.getIdMorador() != null) {
            Optional<Morador> morador = moradorService.getMoradorById(dto.getIdMorador());
            if (!morador.isPresent()) {
                veiculo.setMorador(null);
            } else {
                veiculo.setMorador(morador.get());
            }
        }
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!unidade.isPresent()) {
                veiculo.setUnidade(null);
            } else {
                veiculo.setUnidade(Unidade.get());
            }
        }
        return veiculo;
    }
}
