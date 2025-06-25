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
    private Long idUnidade;
    private Long idMorador;
    private String nomePrestadorServico;
    private String nomeMorador;
    private String numeroUnidade;

    public static ReservaDTO create(Reserva reserva){
        ModelMapper modelMapper = new ModelMapper();
        ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);
        if (reserva.getUnidade() != null) {
            dto.numeroUnidade = reserva.getUnidade().getNumero();
        } else {
            dto.numeroUnidade = null; // Ou "" ou algum valor padrão
        }

        if (reserva.getMorador() != null) {
            dto.nomeMorador = reserva.getMorador().getNome();
        } else {
            dto.nomeMorador = null; // Ou "" ou algum valor padrão
        }

        if (reserva.getPrestadorServico() != null) {
            dto.nomePrestadorServico = reserva.getPrestadorServico().getNome();
        } else {
            dto.nomePrestadorServico = null; // Ou "" ou algum valor padrão
        }

        // Se você também precisa dos IDs das entidades relacionadas, adicione aqui
        if (reserva.getPrestadorServico() != null) {
            dto.idPrestadorServico = reserva.getPrestadorServico().getId();
        }

        if (reserva.getUnidade() != null) {
            dto.idUnidade = reserva.getUnidade().getId();
        }

        if (reserva.getMorador() != null) {
            dto.idMorador = reserva.getMorador().getId();
        }
        return dto;
    }
}
