package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Condominio;
import com.example.condapi.model.entity.Morador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class CondominioDTO {

    private Long id;

    private String nome;
    private String tipocondominio;
    private int quantidadePortarias;
    private int quantidadeUnidades;
    private int quantidadeBlocos;
    private boolean exigeIdentificacao;
    private String chaveAcesso;
    private String logradouro;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static CondominioDTO create(Condominio condominio){
        ModelMapper modelMapper = new ModelMapper();
        CondominioDTO dto = modelMapper.map(condominio, CondominioDTO.class);
        return dto;
    }
}
