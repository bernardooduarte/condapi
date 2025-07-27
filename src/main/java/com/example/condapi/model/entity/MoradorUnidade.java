package com.example.condapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoradorUnidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Morador morador;
    @ManyToOne
    private Unidade unidade;

    @JsonIgnore
    @OneToMany(mappedBy = "moradorUnidade")
    private List<Veiculo> veiculos;
}