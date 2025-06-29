package com.example.condapi.api.controller;

import com.example.condapi.api.dto.ReservaDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.Reserva;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.service.MoradorService;
import com.example.condapi.service.PrestadorServicoService;
import com.example.condapi.service.ReservaService;
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
@RequestMapping("/api/v1/reservas")
public class ReservaController {
    private final ReservaService service;
    private final MoradorService moradorService;
    private final UnidadeService unidadeService;
    private final PrestadorServicoService prestadorServicoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Reserva> reservas = service.getReservas();
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reserva.map(ReservaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ReservaDTO dto) {
        try {
            Reserva reserva = converter(dto);
            reserva = service.salvar(reserva);
            // CORRIGIDO: Retorna o DTO após salvar para evitar problemas de serialização
            return new ResponseEntity(ReservaDTO.create(reserva), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ReservaDTO dto) {
        // Antes de atualizar, verificar se a reserva existe
        Optional<Reserva> existingReserva = service.getReservaById(id);
        if (!existingReserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada para atualização", HttpStatus.NOT_FOUND);
        }
        try {
            Reserva reserva = converter(dto);
            reserva.setId(id); // Manter o ID da reserva existente
            service.salvar(reserva);
            // CORRIGIDO: Retorna o DTO do objeto salvo para evitar problemas de serialização de proxies LAZY
            return ResponseEntity.ok(ReservaDTO.create(reserva));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada para exclusão", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(reserva.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Reserva converter(ReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Reserva reserva = modelMapper.map(dto, Reserva.class);

        // Lógica para associar Morador
        if (dto.getIdMorador() != null) {
            Optional<Morador> morador = moradorService.getMoradorById(dto.getIdMorador());
            if (!morador.isPresent()) {
                reserva.setMorador(null);
            } else {
                reserva.setMorador(morador.get());
            }
        } else {
            reserva.setMorador(null);
        }

        // Lógica para associar Unidade
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!unidade.isPresent()) {
                reserva.setUnidade(null);
            } else {
                reserva.setUnidade(unidade.get());
            }
        } else {
            reserva.setUnidade(null);
        }

        // Lógica para associar PrestadorServico
        if (dto.getIdPrestadorServico() != null) {
            Optional<PrestadorServico> prestadorServico = prestadorServicoService.getPrestadorServicoById(dto.getIdPrestadorServico());
            if (!prestadorServico.isPresent()) {
                reserva.setPrestadorServico(null);
            } else {
                reserva.setPrestadorServico(prestadorServico.get());
            }
        } else {
            reserva.setPrestadorServico(null);
        }

        return reserva;
    }
}
