package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Unidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
