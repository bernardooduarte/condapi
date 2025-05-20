package com.example.condapi.api.controller;

import com.example.condapi.api.dto.AreaComumDTO;
import com.example.condapi.model.entity.AreaComum;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.service.AreaComumService;
import com.example.condapi.service.CondominioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor

public class AreaComumController {




    private final AreaComumService service;
    private final CondominioService condominioService;



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
