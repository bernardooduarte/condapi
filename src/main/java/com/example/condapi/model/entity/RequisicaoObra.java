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

public class RequisicaoObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    private String status;

    @ManyToOne
    MoradorUnidade moradorUnidade;

    @ManyToOne
    RequisicaoObra requisicaoObra;

    @JsonIgnore
    @OneToMany(mappedBy = "requisicaoObra")
    private List<Obra> obras;
}
