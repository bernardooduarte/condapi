package com.example.condapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaComum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private int capacidadeMax;
    private String horarioUtilizacaoInicio;
    private String horarioUtilizacaoFim;
    private String restricoes;
    @ManyToOne
    private Condominio condominio;
}
