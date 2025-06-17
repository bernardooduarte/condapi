package com.example.condapi.api.controller;


import com.example.condapi.api.dto.EncomendaDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.*;
import com.example.condapi.service.*;
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
@RequestMapping("/api/v1/encomendas")

public class EncomendaController {


    private final EncomendaService service;
    private final MoradorService moradorService;
    private final UnidadeService unidadeService;
    private final PorteiroService porteiroService;

    @GetMapping()
    public ResponseEntity get() {
        List<Encomenda> encomendas = service.getEncomendas();
        return ResponseEntity.ok(encomendas.stream().map(EncomendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Encomenda> encomenda = service.getEncomendaById(id);
        if (!encomenda.isPresent()) {
            return new ResponseEntity("Encomenda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(encomenda.map(EncomendaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody EncomendaDTO dto) {
        try {
            Encomenda encomenda = converter(dto);
            encomenda = service.salvar(encomenda);
            return new ResponseEntity(encomenda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EncomendaDTO dto) {
        if (!service.getEncomendaById(id).isPresent()) {
            return new ResponseEntity("Encomenda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Encomenda encomenda = converter(dto);
            encomenda.setId(id);
            service.salvar(encomenda);
            return ResponseEntity.ok(encomenda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


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
            if (!unidade.isPresent()) {
                encomenda.setUnidade(null);
            } else {
                encomenda.setUnidade(unidade.get());
            }
        }
        if (dto.getIdPorteiro() != null) {
            Optional<Porteiro> porteiro = porteiroService.getPorteiroById(dto.getIdPorteiro());
            if (!porteiro.isPresent()) {
                encomenda.setPorteiro(null);
            } else {
                encomenda.setPorteiro(porteiro.get());
            }
        }
        return encomenda;
    }
}
