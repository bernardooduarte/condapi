package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class unidadeDTO {

    private String numero;
    private String rua;
    private String petFriendly;
    private int quantidadeVagas;
    private Long idBloco;
    private String nomeBloco;

    public static UnidadeDTO create(Unidade unidade){
        ModelMapper modelMapper = new ModelMapper();
        UnidadeDTO dto = modelMapper.map(unidade, UnidadeDTO.class);
        dto.nomeBloco = unidade.getBloco().getNome();
        return dto;
    }
}
