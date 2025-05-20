package com.example.condapi.api.controller;

import com.example.condapi.api.dto.BlocoDTO;
import com.example.condapi.api.dto.ObraDTO;
import com.example.condapi.model.entity.*;
import com.example.condapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ObraController {

    private final ObraService service;
    private final PrestadorServicoService prestadorServicoService;
    private final RequisicaoObraService requisicaoObraService;


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
