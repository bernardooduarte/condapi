package com.example.condapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RequisicaoObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Obra obra;
    private String data;
    private PrestadorServico prestadorServico;
    @ManyToOne
    private Morador morador;
    @ManyToOne
    private Unidade unidade;
    private String status;
}
