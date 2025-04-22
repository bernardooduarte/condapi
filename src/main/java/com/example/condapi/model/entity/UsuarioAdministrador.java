package com.example.condapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioAdministrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeEmpresa;
    private String cnpj;
    private String nomeResponsavelComercial;
    private String telefoneComercial;
    private String email;
    private String logradouro;
    private int numero;
    private String complemento;
    private String bairro;
    private String uf;
    private String cep;
    private String senha;
}
