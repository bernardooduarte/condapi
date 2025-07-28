package com.example.condapi.api.controller;


import com.example.condapi.api.dto.AreaComumDTO;
import com.example.condapi.api.dto.CondominioDTO;
import com.example.condapi.api.dto.PorteiroDTO;
import com.example.condapi.api.dto.PrestadorServicoDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.AreaComum;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.model.entity.Porteiro;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.service.CondominioService;
import io.swagger.annotations.Api;
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
@Api("API de Condomínio")
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

    @GetMapping("/{id}/condominios")
    public ResponseEntity getCondominios(@PathVariable("id") Long id) {
        Optional<Condominio> condominio = service.getCondominioById(id);
        if (!condominio.isPresent()) {
            return new ResponseEntity("Condomínio não encontrado", HttpStatus.NOT_FOUND);
        }
        List<Porteiro> porteiros = condominio.get().getPorteiros();
        return ResponseEntity.ok(porteiros.stream().map(PorteiroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}/prestadoresServicos")
    public ResponseEntity getPrestadoresServicos(@PathVariable("id") Long id) {
            Optional<Condominio> condominio= service.getCondominioById(id);
        if (!condominio.isPresent()) {
            return new ResponseEntity("Condomínio não encontrado", HttpStatus.NOT_FOUND);
        }
        List<PrestadorServico> prestadoresServicos = condominio.get().getPrestadoresServicos();
        return ResponseEntity.ok(prestadoresServicos.stream().map(PrestadorServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}/areasComuns")
    public ResponseEntity getAreasComuns(@PathVariable("id") Long id) {
        Optional<Condominio> condominio= service.getCondominioById(id);
        if (!condominio.isPresent()) {
            return new ResponseEntity("Condomínio não encontrado", HttpStatus.NOT_FOUND);
        }
        List<AreaComum> areasComuns = condominio.get().getAreasComuns();
        return ResponseEntity.ok(areasComuns.stream().map(AreaComumDTO::create).collect(Collectors.toList()));
    }
}
