package com.example.condapi.api.dto;


import com.example.condapi.model.entity.Porteiro;
import org.modelmapper.ModelMapper;

public class PorteiroDTO {

    private Long id;

    public static PorteiroDTO create(Porteiro porteiro){
        ModelMapper modelMapper = new ModelMapper();
        PorteiroDTO dto = modelMapper.map(porteiro, porteiroDTO.class);
        return dto;
    }
}
