package com.example.condapi.api.controller;


import com.example.condapi.api.dto.ObraDTO;
import com.example.condapi.api.dto.PorteiroDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.*;
import com.example.condapi.service.*;
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
@Api("API de Obras")
@CrossOrigin
@RequestMapping("/api/v1/obras")

public class ObraController {

    private final ObraService service;
    private final PrestadorServicoService prestadorServicoService;
    private final RequisicaoObraService requisicaoObraService;


    @GetMapping()
    public ResponseEntity get() {
        List<Obra> obras = service.getObras();
        return ResponseEntity.ok(obras.stream().map(ObraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Obra encontrada"),
            @ApiResponse(code = 404, message = "Obra não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Obra> obra = service.getObraById(id);
        if (!obra.isPresent()) {
            return new ResponseEntity("Obra não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(obra.map(ObraDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ObraDTO dto) {
        try {
            Obra obra = converter(dto);
            obra = service.salvar(obra);
            return new ResponseEntity(obra, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ObraDTO dto) {
        if (!service.getObraById(id).isPresent()) {
            return new ResponseEntity("Obra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Obra obra = converter(dto);
            obra.setId(id);
            service.salvar(obra);
            return ResponseEntity.ok(obra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Obra> obra = service.getObraById(id);
        if (!obra.isPresent()) {
            return new ResponseEntity("Obra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(obra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Obra converter(ObraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Obra obra = modelMapper.map(dto, Obra.class);
        if (dto.getIdPrestadorServico() != null) {
            Optional<PrestadorServico> prestadorServico  = prestadorServicoService.getPrestadorServicoById(dto.getIdPrestadorServico());
            if (!prestadorServico.isPresent()) {
                obra.setPrestadorServico(null);
            } else {
                obra.setPrestadorServico(prestadorServico.get());
            }
        }
        if (dto.getIdRequisicaoObra() != null) {
            Optional<RequisicaoObra> requisicaoObra  = requisicaoObraService.getRequisicaoObraById(dto.getIdRequisicaoObra());
            if (!requisicaoObra.isPresent()) {
                obra.setRequisicaoObra(null);
            } else {
                obra.setRequisicaoObra(requisicaoObra.get());
            }
        }
        return obra;
    }
}
