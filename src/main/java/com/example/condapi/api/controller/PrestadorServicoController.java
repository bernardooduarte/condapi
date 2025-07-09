package com.example.condapi.api.controller;

import com.example.condapi.api.dto.PrestadorServicoDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.PrestadorServicoService;
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
@Api("API de Prestadores de Serviço")
@CrossOrigin
@RequestMapping("/api/v1/prestadoresServicos")

public class PrestadorServicoController {

    private final PrestadorServicoService service;
    private final UnidadeService unidadeService;

    @GetMapping()
    public ResponseEntity get() {
        List<PrestadorServico> prestadoresServico = service.getPrestadoresServico();
        return ResponseEntity.ok(prestadoresServico.stream().map(PrestadorServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um prestador de serviço")
    @ApiResponses({
                @ApiResponse(code = 200, message = "Prestador de serviço encontrado"),
                @ApiResponse(code = 404, message = "Prestador de serviço não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<PrestadorServico> prestadorServico = service.getPrestadorServicoById(id);
        if (!prestadorServico.isPresent()) {
            return new ResponseEntity("Prestador Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(prestadorServico.map(PrestadorServicoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody PrestadorServicoDTO dto) {
        try {
            PrestadorServico prestadorServico = converter(dto);
            prestadorServico = service.salvar(prestadorServico);
            // CORRIGIDO: Retorna o DTO após salvar para evitar problemas de serialização
            return new ResponseEntity(PrestadorServicoDTO.create(prestadorServico), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PrestadorServicoDTO dto) {
        if (!service.getPrestadorServicoById(id).isPresent()) {
            return new ResponseEntity("Prestador de Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            PrestadorServico prestadorServico = converter(dto);
            prestadorServico.setId(id);
            service.salvar(prestadorServico);
            return ResponseEntity.ok(prestadorServico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<PrestadorServico> prestadorServico = service.getPrestadorServicoById(id);
        if (!prestadorServico.isPresent()) {
            return new ResponseEntity("Prestador de Serviço não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(prestadorServico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public PrestadorServico converter(PrestadorServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        PrestadorServico prestadorServico = modelMapper.map(dto, PrestadorServico.class);
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!unidade.isPresent()) {
                prestadorServico.setUnidade(null);
            } else {
                prestadorServico.setUnidade(unidade.get());
            }
        }
        return prestadorServico;
    }
}