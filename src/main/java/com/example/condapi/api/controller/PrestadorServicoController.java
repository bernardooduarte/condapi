package com.example.condapi.api.controller;


import com.example.condapi.api.dto.BlocoDTO;
import com.example.condapi.api.dto.PrestadorServicoDTO;
import com.example.condapi.model.entity.Bloco;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.PrestadorServicoService;
import com.example.condapi.service.RequisicaoObraService;
import com.example.condapi.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PrestadorServicoController {

    private final PrestadorServicoService service;
    private final UnidadeService unidadeService;

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
