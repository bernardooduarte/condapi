package com.example.condapi.api.controller;

import com.example.condapi.api.dto.AreaComumDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.AreaComum;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.service.AreaComumService;
import com.example.condapi.service.CondominioService;
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
@Api("API de Área Comum")
@CrossOrigin
@RequestMapping("/api/v1/areasComuns")

public class AreaComumController {

    private final AreaComumService service;
    private final CondominioService condominioService;

    @GetMapping()
    public ResponseEntity get() {
        List<AreaComum> areasComuns = service.getAreasComuns();
        return ResponseEntity.ok(areasComuns.stream().map(AreaComumDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma área comum")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Área comum encontrada"),
            @ApiResponse(code = 404, message = "Área comum não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AreaComum> areaComum = service.getAreaComumById(id);
        if (!areaComum.isPresent()) {
            return new ResponseEntity("Área Comum não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(areaComum.map(AreaComumDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AreaComumDTO dto) {
        try {
            AreaComum areaComum = converter(dto);
            areaComum = service.salvar(areaComum);
            return new ResponseEntity(areaComum, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AreaComumDTO dto) {
        if (!service.getAreaComumById(id).isPresent()) {
            return new ResponseEntity("Área comum não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            AreaComum areaComum = converter(dto);
            areaComum.setId(id);
            service.salvar(areaComum);
            return ResponseEntity.ok(areaComum);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AreaComum> areaComum = service.getAreaComumById(id);
        if (!areaComum.isPresent()) {
            return new ResponseEntity("Area comum não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(areaComum.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AreaComum converter(AreaComumDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AreaComum areaComum = modelMapper.map(dto, AreaComum.class);
        if (dto.getIdCondominio() != null) {
            Optional<Condominio> condominio = condominioService.getCondominioById(dto.getIdCondominio());
            if (!condominio.isPresent()) {
                areaComum.setCondominio(null);
            } else {
                areaComum.setCondominio(condominio.get());
            }
        }
        return areaComum;
    }
}
