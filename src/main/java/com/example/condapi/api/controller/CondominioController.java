package com.example.condapi.api.controller;


import com.example.condapi.api.dto.CondominioDTO;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.service.CondominioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CondominioController {

    private final CondominioService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Condominio> condominios = service.getCondominios();
        return ResponseEntity.ok(condominios.stream().map(CondominioDTO::create).collect(Collectors.toList()));
    }

    public Condominio converter(CondominioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Condominio condominio = modelMapper.map(dto, Condominio.class);
        return condominio;
    }
}
