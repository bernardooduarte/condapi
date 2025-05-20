package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Unidade;
import org.modelmapper.ModelMapper;

public class UnidadeDTO
{
    private String numero;
    private String rua;
    private String petFriendly;
    private Integer quantidadeVagas;
    private Long idBloco;
    private String nomeBloco;

    public static UnidadeDTO create(Unidade unidade){
        ModelMapper modelMapper = new ModelMapper();
        UnidadeDTO dto = modelMapper.map(unidade, UnidadeDTO.class);
        dto.nomeBloco = unidade.getBloco().getNome();
        return dto;
    }
}
