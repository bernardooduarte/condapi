package com.example.condapi.api.controller;

import com.example.condapi.api.dto.MoradorDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.MoradorService;
import com.example.condapi.service.UnidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api("API de Morador")
@CrossOrigin
@RequestMapping("/api/v1/moradores")

public class MoradorController {
    private final MoradorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Morador> moradores = service.getMoradores();
        return ResponseEntity.ok(moradores.stream().map(MoradorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um morador")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Morador encontrado"),
            @ApiResponse(code = 404, message = "Morador não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Morador> morador = service.getMoradorById(id);
        if (!morador.isPresent()) {
            return new ResponseEntity("Morador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(morador.map(MoradorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody MoradorDTO dto) {
        try {
            Morador morador = converter(dto);
            morador = service.salvar(morador);
            return new ResponseEntity(morador, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody MoradorDTO dto) {
        if (!service.getMoradorById(id).isPresent()) {
            return new ResponseEntity("Morador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Morador morador = converter(dto);
            morador.setId(id);
            service.salvar(morador);
            return ResponseEntity.ok(morador);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Morador> morador = service.getMoradorById(id);
        if (!morador.isPresent()) {
            return new ResponseEntity("Morador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(morador.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Morador converter(MoradorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Morador morador = modelMapper.map(dto, Morador.class);
        return morador;
    }
}
