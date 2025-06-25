package com.example.condapi.api.controller;


import com.example.condapi.api.dto.CondominioDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.service.CondominioService;
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
@RequestMapping("/api/v1/condominios")
public class CondominioController {

    private final CondominioService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Condominio> condominios = service.getCondominios();
        return ResponseEntity.ok(condominios.stream().map(CondominioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Condominio> condominio = service.getCondominioById(id);
        if (!condominio.isPresent()) {
            return new ResponseEntity("Condomínio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(condominio.map(CondominioDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody CondominioDTO dto) {
        try {
            Condominio condominio = converter(dto);
            condominio = service.salvar(condominio);
            return new ResponseEntity(condominio, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CondominioDTO dto) {
        if (!service.getCondominioById(id).isPresent()) {
            return new ResponseEntity("Condomínio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Condominio condominio = converter(dto);
            condominio.setId(id);
            service.salvar(condominio);
            return ResponseEntity.ok(condominio);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Condominio> condominio = service.getCondominioById(id);
        if (!condominio.isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(condominio.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Condominio converter(CondominioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Condominio condominio = modelMapper.map(dto, Condominio.class);
        return condominio;
    }
}
