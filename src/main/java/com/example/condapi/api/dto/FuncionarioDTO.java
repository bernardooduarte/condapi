package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FuncionarioDTO {

    private Long id;

    private String nome;
    private String cpf;
    private Long idCondominio;
    private String empresa;
    private String celularComercial;
    private String email;
    private String nomeCondominio;

    public static FuncionarioDTO create(Funcionario funcionario){
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);
        dto.nomeCondominio = funcionario.getCondominio().getNome();
        return dto;
    }
}
