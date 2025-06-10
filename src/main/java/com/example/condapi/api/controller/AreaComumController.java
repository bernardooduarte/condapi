package com.example.condapi.api.controller;

import com.example.condapi.api.dto.AreaComumDTO;
import com.example.condapi.model.entity.AreaComum;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.service.AreaComumService;
import com.example.condapi.service.CondominioService;
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
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AreaComum> areaComum = service.getAreaComumById(id);
        if (!areaComum.isPresent()) {
            return new ResponseEntity("Área Comum não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(areaComum.map(AreaComumDTO::create));
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
