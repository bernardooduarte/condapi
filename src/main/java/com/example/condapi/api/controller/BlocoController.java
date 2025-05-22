package com.example.condapi.api.controller;

import com.example.condapi.api.dto.BlocoDTO;
import com.example.condapi.model.entity.Bloco;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.service.BlocoService;
import com.example.condapi.service.CondominioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/blocos")

public class BlocoController {

    private final BlocoService service;
    private final CondominioService condominioService;




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
}
