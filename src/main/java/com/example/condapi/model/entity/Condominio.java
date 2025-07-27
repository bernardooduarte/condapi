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

public class Condominio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipoCondominio;
    private Integer quantidadePortarias;
    private Integer quantidadeUnidades;
    private Integer quantidadeBlocos;
    private String exigeIdentificacao;
    private String chaveAcesso;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    @JsonIgnore
    @OneToMany(mappedBy = "condominio")
    private List<Porteiro> porteiros;
}
