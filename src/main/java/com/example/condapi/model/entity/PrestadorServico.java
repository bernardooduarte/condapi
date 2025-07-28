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
public class PrestadorServico extends Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Unidade unidade;
    @ManyToOne
    private Condominio condominio;

    @JsonIgnore
    @OneToMany(mappedBy = "prestadorServico")
    private List<Reserva> reservas;

    @JsonIgnore
    @OneToMany(mappedBy = "prestadorServico")
    private List<Obra> obras;
}
