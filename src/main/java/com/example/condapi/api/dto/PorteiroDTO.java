package com.example.condapi.api.dto;


import com.example.condapi.model.entity.Porteiro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PorteiroDTO {

    private Long id;
    private String nome;
    private String cpf;

    public static PorteiroDTO create(Porteiro porteiro){
        ModelMapper modelMapper = new ModelMapper();
        PorteiroDTO dto = modelMapper.map(porteiro, PorteiroDTO.class);
        return dto;
    }
}
