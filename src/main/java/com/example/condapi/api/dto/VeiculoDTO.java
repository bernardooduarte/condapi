package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Funcionario;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.model.entity.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class VeiculoDTO {

    private Long id;

    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private String tipoVeiculo;
    private Long idMoradorUnidade;
    private String numeroUnidade;
    private String nomeMorador;

    public static VeiculoDTO create(Veiculo veiculo){
        ModelMapper modelMapper = new ModelMapper();
        VeiculoDTO dto = modelMapper.map(veiculo, VeiculoDTO.class);
        dto.numeroUnidade = veiculo.getMoradorUnidade().getUnidade().getNumero();
        dto.nomeMorador = veiculo.getMoradorUnidade().getMorador().getNome();
        return dto;
    }
}
