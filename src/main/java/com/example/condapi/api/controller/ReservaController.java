package com.example.condapi.api.controller;

import com.example.condapi.api.dto.EncomendaDTO;
import com.example.condapi.api.dto.ReservaDTO;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.*;
import com.example.condapi.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api("API de Reservas")
@CrossOrigin
@RequestMapping("/api/v1/reservas")
public class ReservaController {
    private final ReservaService service;
    private final MoradorUnidadeService moradorUnidadeService;
    private final PrestadorServicoService prestadorServicoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Reserva> reservas = service.getReservas();
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma reserva")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reserva encontrada"),
            @ApiResponse(code = 404, message = "Reserva não encontrada")
    })
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
        if (dto.getIdMoradorUnidade() != null) {
            Optional<MoradorUnidade> moradorUnidade = moradorUnidadeService.getMoradorUnidadeById(dto.getIdMoradorUnidade());
            if (!moradorUnidade.isPresent()) {
                reserva.setMoradorUnidade(null);
            } else {
                reserva.setMoradorUnidade(moradorUnidade.get());
            }
        }if (dto.getIdPrestadorServico() != null) {
            Optional<PrestadorServico> prestadorServico = prestadorServicoService.getPrestadorServicoById(dto.getIdPrestadorServico());
            if (!prestadorServico.isPresent()) {
                reserva.setPrestadorServico(null);
            } else {
                reserva.setPrestadorServico(prestadorServico.get());
            }
        }
        return reserva;
    }
}
