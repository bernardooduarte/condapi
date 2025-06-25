package com.example.condapi.api.controller;

import com.example.condapi.api.dto.PrestadorServicoDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.PrestadorServicoService;
import com.example.condapi.service.UnidadeService;
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
@RequestMapping("/api/v1/prestadoresServicos")

public class PrestadorServicoController {

    private final PrestadorServicoService service;
    private final UnidadeService unidadeService;

    @GetMapping()
    public ResponseEntity get() {
        List<PrestadorServico> prestadoresServico = service.getPrestadoresServico();
        return ResponseEntity.ok(prestadoresServico.stream().map(PrestadorServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<PrestadorServico> prestadorServico = service.getPrestadorServicoById(id);
        if (!prestadorServico.isPresent()) {
            return new ResponseEntity("Prestador Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(prestadorServico.map(PrestadorServicoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody PrestadorServicoDTO dto) {
        try {
            PrestadorServico prestadorServico = converter(dto);
            prestadorServico = service.salvar(prestadorServico);
            // CORRIGIDO: Retorna o DTO após salvar para evitar problemas de serialização
            return new ResponseEntity(PrestadorServicoDTO.create(prestadorServico), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PrestadorServicoDTO dto) {
        // Antes de atualizar, verificar se o prestador de serviço existe
        Optional<PrestadorServico> existingPrestadorServico = service.getPrestadorServicoById(id);
        if (!existingPrestadorServico.isPresent()) {
            return new ResponseEntity("Prestador de Serviço não encontrado para atualização", HttpStatus.NOT_FOUND);
        }
        try {
            PrestadorServico prestadorServico = converter(dto);
            prestadorServico.setId(id);
            // Preserva o ID do funcionário base se ele já existe e não foi passado no DTO
            // ou se a lógica do converter não trata IDs de herança JOINED.
            // Aqui estamos salvando a entidade já com o ID setado.
            prestadorServico = service.salvar(prestadorServico);
            // CORRIGIDO: Retorna o DTO do objeto salvo para evitar problemas de serialização de proxies LAZY
            return ResponseEntity.ok(PrestadorServicoDTO.create(prestadorServico));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<PrestadorServico> prestadorServico = service.getPrestadorServicoById(id);
        if (!prestadorServico.isPresent()) {
            return new ResponseEntity("Prestador de Serviço não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(prestadorServico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public PrestadorServico converter(PrestadorServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        // Mapeia os campos comuns (nome, cpf) da hierarquia Funcionario para PrestadorServico
        PrestadorServico prestadorServico = modelMapper.map(dto, PrestadorServico.class);

        // Associa a Unidade se um idUnidade for fornecido
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!unidade.isPresent()) {
                // Se a unidade não for encontrada, você pode definir como null ou lançar exceção
                prestadorServico.setUnidade(null); // Conforme optional = true na entidade
            } else {
                prestadorServico.setUnidade(unidade.get());
            }
        } else {
            prestadorServico.setUnidade(null); // Se o ID da unidade não for fornecido no DTO
        }
        return prestadorServico;
    }
}