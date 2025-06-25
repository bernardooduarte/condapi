package com.example.condapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String horaInicio;
    private String horaFim;
    private String data;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private PrestadorServico prestadorServico;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Unidade unidade;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Morador morador;
}
