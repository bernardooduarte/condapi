package com.example.condapi.api.controller;

import com.example.condapi.api.dto.FuncionarioDTO;
import com.example.condapi.model.entity.Funcionario;
import com.example.condapi.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/funcionarios")

public class FuncionarioController {
    private final FuncionarioService funcionarioService;
    @GetMapping()
    public ResponseEntity get() {
        List<Funcionario> funcionarios = funcionarioService.getFuncionarios();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }
}
