package com.example.condapi.api.controller;

import com.example.condapi.api.dto.MoradorUnidadeDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.MoradorUnidade;
import com.example.condapi.model.entity.Porteiro;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.MoradorService;
import com.example.condapi.service.MoradorUnidadeService;
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
@Api("API da associação Morador/Unidade")
@CrossOrigin
@RequestMapping("/api/v1/moradoresUnidades")
public class MoradorUnidadeController {

    private final MoradorUnidadeService service;
    private final MoradorService moradorService;
    private final UnidadeService unidadeService;

    @GetMapping()
    public ResponseEntity get() {
        List<MoradorUnidade> moradorUnidades = service.getMoradorUnidades();
        return ResponseEntity.ok(moradorUnidades.stream().map(MoradorUnidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma associação")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Associação encontrada"),
            @ApiResponse(code = 404, message = "Associação não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<MoradorUnidade> moradorUnidade = service.getMoradorUnidadeById(id);
        if (!moradorUnidade.isPresent()) {
            return new ResponseEntity("Associação Morador/Unidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(moradorUnidade.map(MoradorUnidadeDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody MoradorUnidadeDTO dto) {
        try {
            MoradorUnidade moradorUnidade = converter(dto);
            moradorUnidade = service.salvar(moradorUnidade);
            return new ResponseEntity(MoradorUnidadeDTO.create(moradorUnidade), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody MoradorUnidadeDTO dto) {
        if (!service.getMoradorUnidadeById(id).isPresent()) {
            return new ResponseEntity("Associação Morador/Unidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            MoradorUnidade moradorUnidade = converter(dto);
            moradorUnidade.setId(id);
            moradorUnidade = service.salvar(moradorUnidade);
            return ResponseEntity.ok(MoradorUnidadeDTO.create(moradorUnidade));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<MoradorUnidade> moradorUnidade = service.getMoradorUnidadeById(id);
        if (!moradorUnidade.isPresent()) {
            return new ResponseEntity("Associação Morador/Unidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(moradorUnidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public MoradorUnidade converter(MoradorUnidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        MoradorUnidade moradorUnidade = modelMapper.map(dto, MoradorUnidade.class);

        if (dto.getIdMorador() != null) {
            Optional<Morador> morador = moradorService.getMoradorById(dto.getIdMorador());
            if (!morador.isPresent()) {
                moradorUnidade.setMorador(null);
            } else {
                moradorUnidade.setMorador(morador.get());
            }
        }

        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!unidade.isPresent()) {
                moradorUnidade.setUnidade(null);
            } else {
                moradorUnidade.setUnidade(unidade.get());
            }
        }

        return moradorUnidade;
    }



}