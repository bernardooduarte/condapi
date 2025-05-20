package com.example.condapi.api.dto;

import com.example.condapi.model.entity.AreaComum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AreaComumDTO {

    private Long id;

    private String nome;
    private String descricao;
    private Integer capacidadeMax;
    private String horarioUtilizacaoInicio;
    private String horarioUtilizacaoFim;
    private String restricoes;
    private Long idCondominio;
    private String nomeCondominio;

    public static AreaComumDTO create(AreaComum areaComum){
        ModelMapper modelMapper = new ModelMapper();
        AreaComumDTO dto = modelMapper.map(areaComum, AreaComumDTO.class);
        dto.nomeCondominio = areaComum.getCondominio().getNome();
        return dto;
    }
}
