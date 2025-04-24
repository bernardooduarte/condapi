package com.example.condapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String dataInicio;
    private String dataFim;
    @ManyToOne
    private PrestadorServico prestadorServico;
    @ManyToOne
    private RequisicaoObra requisicaoObra;
}
