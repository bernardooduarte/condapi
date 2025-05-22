package com.example.condapi.api.controller;


import com.example.condapi.model.entity.Porteiro;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/porteiros")

public class PorteiroController {
    private final PorteiroService service;

    public Porteiro converter(PorteiroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Porteiro porteiro = modelMapper.map(dto, Porteiro.class);
        return porteiro;
    }
}
