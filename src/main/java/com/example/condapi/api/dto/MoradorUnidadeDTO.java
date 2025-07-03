package com.example.condapi.api.dto;

import com.example.condapi.model.entity.MoradorUnidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoradorUnidadeDTO {

    private Long id;

    private Long idMorador;
    private String nomeMorador;
    private Long idUnidade;
    private String numeroUnidade;


    public static MoradorUnidadeDTO create(MoradorUnidade moradorUnidade) {
        ModelMapper modelMapper = new ModelMapper();
        MoradorUnidadeDTO dto = modelMapper.map(moradorUnidade, MoradorUnidadeDTO.class);
        dto.nomeMorador = moradorUnidade.getMorador().getNome();
        dto.numeroUnidade = moradorUnidade.getUnidade().getNumero();
        return dto;
    }

}
