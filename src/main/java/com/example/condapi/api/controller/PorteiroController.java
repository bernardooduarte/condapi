package com.example.condapi.api.controller;


import com.example.condapi.api.dto.MoradorDTO;
import com.example.condapi.api.dto.PorteiroDTO;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.Porteiro;
import com.example.condapi.service.PorteiroService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/porteiros")

public class PorteiroController {
    private final PorteiroService porteiroService;

    @GetMapping()
    public ResponseEntity get() {
        List<Porteiro> porteiros = porteiroService.getPorteiros();
        return ResponseEntity.ok(porteiros.stream().map(PorteiroDTO::create).collect(Collectors.toList()));
    }

    public Porteiro converter(PorteiroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Porteiro porteiro = modelMapper.map(dto, Porteiro.class);
        return porteiro;
    }
}
