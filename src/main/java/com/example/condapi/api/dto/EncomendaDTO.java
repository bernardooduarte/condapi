package com.example.condapi.api.dto;

import com.example.condapi.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class EncomendaDTO {

    private Long id;
    private Long idMorador;
    private String nomeMorador;
    private String data;
    private String hora;
    private Long idPorteiro;
    private Long idUnidade;
    private String nomePorteiro;
    private String numeroUnidade;

    public static EncomendaDTO create(Encomenda encomenda){
        ModelMapper modelMapper = new ModelMapper();
        EncomendaDTO dto = modelMapper.map(encomenda, EncomendaDTO.class);
        dto.nomePorteiro = encomenda.getPorteiro().getNome();
        dto.nomeMorador = encomenda.getMorador().getNome();
        dto.numeroUnidade = encomenda.getUnidade().getNumero();
        return dto;
    }
}
