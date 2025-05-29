package com.example.condapi.api.controller;

import com.example.condapi.api.dto.MoradorDTO;
import com.example.condapi.api.dto.PrestadorServicoDTO;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.PrestadorServicoService;
import com.example.condapi.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/prestadoresServicos")

public class PrestadorServicoController {

    private final PrestadorServicoService prestadorServicoService;
    private final UnidadeService unidadeService;

    @GetMapping()
    public ResponseEntity get() {
        List<PrestadorServico> prestadoresServico = prestadorServicoService.getPrestadoresServico();
        return ResponseEntity.ok(prestadoresServico.stream().map(PrestadorServicoDTO::create).collect(Collectors.toList()));
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
