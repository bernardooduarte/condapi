package com.example.condapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String rua;
    private String petFriendly;
    @ManyToOne
    private Bloco bloco;
    private String quantidadeVagas;

    @JsonIgnore
    @OneToMany(mappedBy = "unidade")
    private List<PrestadorServico> prestadoresServicos;
}
