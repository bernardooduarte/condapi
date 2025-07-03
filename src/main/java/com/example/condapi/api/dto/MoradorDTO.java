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
    private String nome;
    private String cpf;
    private String celularPessoal;
    private String celularComercial;
    private String email;
    private String statusProprietario;

    public static MoradorDTO create(Morador morador){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(morador, MoradorDTO.class);
    }
}
