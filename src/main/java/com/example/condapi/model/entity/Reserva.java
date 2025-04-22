package com.example.condapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor

public abstract class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String horaInicio;
    private String horaFim;
    private String data;
    private PrestadorServico prestadorServico;
    private Unidade unidade;
    private Morador morador;
}
