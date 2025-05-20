package com.example.condapi.api.controller;


import com.example.condapi.model.entity.Porteiro;

public class PorteiroController {
    private final PorteiroService service;

    public Porteiro converter(PorteiroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Porteiro porteiro = modelMapper.map(dto, Porteiro.class);
        return porteiro;
    }
}
