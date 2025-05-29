package com.example.condapi.api.controller;

import com.example.condapi.api.dto.AreaComumDTO;
import com.example.condapi.model.entity.AreaComum;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.service.AreaComumService;
import com.example.condapi.service.CondominioService;
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
@RequestMapping("/api/v1/areasComuns")

public class AreaComumController {

    private final AreaComumService areaComumService;
    private final CondominioService condominioService;

    @GetMapping()
    public ResponseEntity get() {
        List<AreaComum> areasComuns = areaComumService.getAreasComuns();
        return ResponseEntity.ok(areasComuns.stream().map(AreaComumDTO::create).collect(Collectors.toList()));
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
