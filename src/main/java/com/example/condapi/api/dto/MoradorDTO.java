package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.Unidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MoradorDTO {

    private Long id;
    private Long idUnidade;
    private String nome;
    private String cpf;
    private String celularPessoal;
    private String celularComercial;
    private String email;
    private boolean statusProprietario;
    private String numeroUnidade;

    public static MoradorDTO create(Morador morador){
        ModelMapper modelMapper = new ModelMapper();
        MoradorDTO dto = modelMapper.map(morador, MoradorDTO.class);
        dto.numeroUnidade = morador.getUnidade().getNumero();
        return dto;
    }
}
