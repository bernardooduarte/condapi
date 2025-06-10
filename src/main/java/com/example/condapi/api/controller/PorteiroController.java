package com.example.condapi.api.controller;


import com.example.condapi.api.dto.MoradorDTO;
import com.example.condapi.api.dto.PorteiroDTO;
import com.example.condapi.api.dto.UnidadeDTO;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.Porteiro;
import com.example.condapi.model.entity.Unidade;
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

    public Porteiro converter(PorteiroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Porteiro porteiro = modelMapper.map(dto, Porteiro.class);
        return porteiro;
    }
}
