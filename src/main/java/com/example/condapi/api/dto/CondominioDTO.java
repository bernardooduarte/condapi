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

    public static CondominioDTO create(Condominio condominio){
        ModelMapper modelMapper = new ModelMapper();
        CondominioDTO dto = modelMapper.map(condominio, CondominioDTO.class);
        return dto;
    }
}
