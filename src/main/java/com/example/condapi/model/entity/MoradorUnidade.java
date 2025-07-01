package com.example.condapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoradorUnidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // Garante que a associação sempre terá um morador
    @JoinColumn(name = "morador_id")
    private Morador morador;

    @ManyToOne(optional = false) // Garante que a associação sempre terá uma unidade
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    // Campos adicionais que descrevem a relação
    // O campo 'statusProprietario' foi movido para cá, o que faz mais sentido
    private String tipoDeVinculo; // Ex: "Proprietário", "Inquilino", "Dependente"
    private String dataInicioMoradia;
    private boolean isResponsavelFinanceiro;

}