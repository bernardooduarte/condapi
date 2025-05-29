package com.example.condapi.api.controller;

import com.example.condapi.api.dto.MoradorDTO;
import com.example.condapi.api.dto.ObraDTO;
import com.example.condapi.model.entity.*;
import com.example.condapi.service.*;
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
@RequestMapping("/api/v1/obras")

public class ObraController {

    private final ObraService obraService;
    private final PrestadorServicoService prestadorServicoService;
    private final RequisicaoObraService requisicaoObraService;


    @GetMapping()
    public ResponseEntity get() {
        List<Obra> obras = obraService.getObras();
        return ResponseEntity.ok(obras.stream().map(ObraDTO::create).collect(Collectors.toList()));
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
