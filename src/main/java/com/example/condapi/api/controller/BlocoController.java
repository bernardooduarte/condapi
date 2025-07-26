package com.example.condapi.api.controller;


import com.example.condapi.api.dto.BlocoDTO;
import com.example.condapi.api.dto.UnidadeDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Bloco;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.BlocoService;
import com.example.condapi.service.CondominioService;
import com.example.condapi.service.UnidadeService;
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
@CrossOrigin
@RequestMapping("/api/v1/blocos")

public class BlocoController {

    private final BlocoService service;
    private final CondominioService condominioService;

    @GetMapping()
    public ResponseEntity get() {
        List<Bloco> blocos = service.getBlocos();
        return ResponseEntity.ok(blocos.stream().map(BlocoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um bloco")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bloco encontrado"),
            @ApiResponse(code = 404, message = "Bloco não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Bloco> bloco = service.getBlocoById(id);
        if (!bloco.isPresent()) {
            return new ResponseEntity("Bloco não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bloco.map(BlocoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody BlocoDTO dto) {
        try {
            Bloco bloco = converter(dto);
            bloco = service.salvar(bloco);
            return new ResponseEntity(bloco, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody BlocoDTO dto) {
        if (!service.getBlocoById(id).isPresent()) {
            return new ResponseEntity("Bloco não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Bloco bloco = converter(dto);
            bloco.setId(id);
            service.salvar(bloco);
            return ResponseEntity.ok(bloco);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Bloco> bloco = service.getBlocoById(id);
        if (!bloco.isPresent()) {
            return new ResponseEntity("Bloco não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(bloco.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Bloco converter(BlocoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Bloco bloco = modelMapper.map(dto, Bloco.class);
        if (dto.getIdCondominio() != null) {
            Optional<Condominio> condominio = condominioService.getCondominioById(dto.getIdCondominio());
            if (!condominio.isPresent()) {
                bloco.setCondominio(null);
            } else {
                bloco.setCondominio(condominio.get());
            }
        }
        return bloco;
    }

    @GetMapping("/{id}/unidades")
    public ResponseEntity getUnidades(@PathVariable("id") Long id) {
        Optional<Bloco> bloco = service.getBlocoById(id);
        if (!bloco.isPresent()) {
            return new ResponseEntity("Bloco não encontrado", HttpStatus.NOT_FOUND);
        }
        List<Unidade> unidades = bloco.get().getUnidades();
        return ResponseEntity.ok(unidades.stream().map(UnidadeDTO::create).collect(Collectors.toList()));
    }
}
