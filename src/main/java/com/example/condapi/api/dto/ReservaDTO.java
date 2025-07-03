package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.Reserva;
import com.example.condapi.model.entity.Unidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReservaDTO {

    private Long id;

    private String horaInicio;
    private String horaFim;
    private String data;
    private Long idPrestadorServico;
    private Long idMoradorUnidade;
    private String nomePrestadorServico;
    private String nomeMorador;
    private String numeroUnidade;

    public static ReservaDTO create(Reserva reserva){
        ModelMapper modelMapper = new ModelMapper();
        ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);
        dto.numeroUnidade = reserva.getMoradorUnidade().getUnidade().getNumero();
        dto.nomeMorador = reserva.getMoradorUnidade().getMorador().getNome();
        dto.nomePrestadorServico = reserva.getPrestadorServico().getNome();
        return dto;
    }
}
