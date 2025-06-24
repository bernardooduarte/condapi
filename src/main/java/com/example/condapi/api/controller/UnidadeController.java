package com.example.condapi.api.controller;


import com.example.condapi.api.dto.UnidadeDTO;
import com.example.condapi.api.dto.VeiculoDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Bloco;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.model.entity.Veiculo;
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
@RequestMapping("/api/v1/unidades")

public class UnidadeController {

    private final UnidadeService service;
    private final BlocoService blocoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Unidade> unidades = service.getUnidades();
        return ResponseEntity.ok(unidades.stream().map(UnidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Unidade> unidade = service.getUnidadeById(id);
        if (!unidade.isPresent()) {
            return new ResponseEntity("unidade não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(unidade.map(UnidadeDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody UnidadeDTO dto) {
        try {
            Unidade unidade = converter(dto);
            unidade = service.salvar(unidade);
            return new ResponseEntity(unidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody UnidadeDTO dto) {
        if (!service.getUnidadeById(id).isPresent()) {
            return new ResponseEntity("Unidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Unidade unidade = converter(dto);
            unidade.setId(id);
            service.salvar(unidade);
            return ResponseEntity.ok(unidade);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Unidade> unidade = service.getUnidadeById(id);
        if (!unidade.isPresent()) {
            return new ResponseEntity("Unidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(unidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Unidade converter(UnidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Unidade unidade = modelMapper.map(dto, Unidade.class);
        if (dto.getIdBloco() != null) {
            Optional<Bloco> bloco = blocoService.getBlocoById(dto.getIdBloco());
            if (!bloco.isPresent()) {
                unidade.setBloco(null);
            } else {
               unidade.setBloco(bloco.get());
            }
        }
        return unidade;
    }
}
