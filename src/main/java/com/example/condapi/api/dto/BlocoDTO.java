package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Bloco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BlocoDTO {

    private Long id;
    private Long idCondominio;
    private String logradouro;
    private String nome;
    private String nomeCondominio;

    public static BlocoDTO create(Bloco bloco){
        ModelMapper modelMapper = new ModelMapper();
        BlocoDTO dto = modelMapper.map(bloco, BlocoDTO.class);
        dto.nomeCondominio = bloco.getCondominio().getNome();
        return dto;
    }
}
