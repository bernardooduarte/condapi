package com.example.condapi.api.controller;


import com.example.condapi.api.dto.RequisicaoObraDTO;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.RequisicaoObra;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.RequisicaoObraService;
import com.example.condapi.service.MoradorService;
import com.example.condapi.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/requisicoesObras")

public class RequisicaoObraController {
    private final RequisicaoObraService service;
    private final MoradorService moradorService;
    private final UnidadeService unidadeService;

    public RequisicaoObra converter(RequisicaoObraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        RequisicaoObra requisicaoObra = modelMapper.map(dto, RequisicaoObra.class);
        if (dto.getIdMorador() != null) {
            Optional<Morador> morador = moradorService.getMoradorById(dto.getIdMorador());
            if (!morador.isPresent()) {
                requisicaoObra.setMorador(null);
            } else {
                requisicaoObra.setMorador(morador.get());
            }
        }
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!unidade.isPresent()) {
                requisicaoObra.setUnidade(null);
            } else {
                requisicaoObra.setUnidade(Unidade.get());
            }
        }
        return requisicaoObra;
    }
}
