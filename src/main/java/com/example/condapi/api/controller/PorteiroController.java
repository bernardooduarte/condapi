package com.example.condapi.api.controller;


import com.example.condapi.api.dto.*;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Porteiro;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.service.PorteiroService;
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
@RequestMapping("/api/v1/porteiros")

public class PorteiroController {
    private final PorteiroService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Porteiro> porteiros = service.getPorteiros();
        return ResponseEntity.ok(porteiros.stream().map(PorteiroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Porteiro> porteiro = service.getPorteiroById(id);
        if (!porteiro.isPresent()) {
            return new ResponseEntity("Porteiro não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(porteiro.map(PorteiroDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody PorteiroDTO dto) {
        try {
            Porteiro porteiro = converter(dto);
            porteiro = service.salvar(porteiro);
            return new ResponseEntity(porteiro, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PorteiroDTO dto) {
        if (!service.getPorteiroById(id).isPresent()) {
            return new ResponseEntity("Porteiro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Porteiro porteiro = converter(dto);
            porteiro.setId(id);
            service.salvar(porteiro);
            return ResponseEntity.ok(porteiro);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Porteiro> porteiro = service.getPorteiroById(id);
        if (!porteiro.isPresent()) {
            return new ResponseEntity("Porteiro não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(porteiro.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Porteiro converter(PorteiroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Porteiro porteiro = modelMapper.map(dto, Porteiro.class);
        return porteiro;
    }
}
