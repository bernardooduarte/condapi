package com.example.condapi.api.controller;


import com.example.condapi.api.dto.MoradorDTO;
import com.example.condapi.api.dto.VeiculoDTO;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.model.entity.Veiculo;
import com.example.condapi.service.MoradorService;
import com.example.condapi.service.UnidadeService;
import com.example.condapi.service.VeiculoService;
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
@RequestMapping("/api/v1/veiculos")


public class VeiculoController {
    private final VeiculoService service;
    private final MoradorService moradorService;
    private final UnidadeService unidadeService;

    @GetMapping()
    public ResponseEntity get() {
        List<Veiculo> veiculos = service.getVeiculos();
        return ResponseEntity.ok(veiculos.stream().map(VeiculoDTO::create).collect(Collectors.toList()));
    }

    public Veiculo converter(VeiculoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);
        if (dto.getIdMorador() != null) {
            Optional<Morador> morador = moradorService.getMoradorById(Long.valueOf(dto.getIdMorador()));
            if (!morador.isPresent()) {
                veiculo.setMorador(null);
            } else {
                veiculo.setMorador(morador.get());
            }
        }
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(Long.valueOf(dto.getIdUnidade()));
            if (!unidade.isPresent()) {
                veiculo.setUnidade(null);
            } else {
                veiculo.setUnidade(unidade.get());
            }
        }
        return veiculo;
    }
}
