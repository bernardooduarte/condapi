package com.example.condapi.api.controller;


import com.example.condapi.api.dto.MoradorDTO;
import com.example.condapi.api.dto.RequisicaoObraDTO;
import com.example.condapi.api.dto.UnidadeDTO;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.RequisicaoObra;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.RequisicaoObraService;
import com.example.condapi.service.MoradorService;
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
@RequestMapping("/api/v1/requisicoesObras")

public class RequisicaoObraController {
    private final RequisicaoObraService service;
    private final MoradorService moradorService;
    private final UnidadeService unidadeService;

    @GetMapping()
    public ResponseEntity get() {
        List<RequisicaoObra> requisicoesObra = service.getRequisicoesObra();
        return ResponseEntity.ok(requisicoesObra.stream().map(RequisicaoObraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<RequisicaoObra> requisicaoObra = service.getRequisicaoObraById(id);
        if (!requisicaoObra.isPresent()) {
            return new ResponseEntity("Requisicao não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(requisicaoObra.map(RequisicaoObraDTO::create));
    }

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
                requisicaoObra.setUnidade(unidade.get());
            }
        }
        return requisicaoObra;
    }
}
