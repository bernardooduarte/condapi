package com.example.condapi.api.controller;


import com.example.condapi.api.dto.VeiculoDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.MoradorUnidade;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.model.entity.Veiculo;
import com.example.condapi.service.MoradorUnidadeService;
import com.example.condapi.service.VeiculoService;
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
@RequestMapping("/api/v1/veiculos")


public class VeiculoController {
    private final VeiculoService service;
    private final MoradorUnidadeService moradorUnidadeService;

    @GetMapping()
    public ResponseEntity get() {
        List<Veiculo> veiculos = service.getVeiculos();
        return ResponseEntity.ok(veiculos.stream().map(VeiculoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Veiculo> veiculo = service.getVeiculoById(id);
        if (!veiculo.isPresent()) {
            return new ResponseEntity("Veículo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(veiculo.map(VeiculoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody VeiculoDTO dto) {
        try {
            Veiculo veiculo = converter(dto);
            veiculo = service.salvar(veiculo);
            return new ResponseEntity(veiculo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody VeiculoDTO dto) {
        if (!service.getVeiculoById(id).isPresent()) {
            return new ResponseEntity("Veículo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Veiculo veiculo = converter(dto);
            veiculo.setId(id);
            service.salvar(veiculo);
            return ResponseEntity.ok(veiculo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Veiculo> veiculo = service.getVeiculoById(id);
        if (!veiculo.isPresent()) {
            return new ResponseEntity("Veículo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(veiculo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Veiculo converter(VeiculoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);

        if (dto.getIdMoradorUnidade() != null) {
            Optional<MoradorUnidade> moradorUnidade = moradorUnidadeService.getMoradorUnidadeById(dto.getIdMoradorUnidade());
            if (!moradorUnidade.isPresent()) {
                veiculo.setMoradorUnidade(null);
            } else {
                veiculo.setMoradorUnidade(moradorUnidade.get());
            }
        }

        return veiculo;
    }

}
