package com.example.condapi.api.dto;

import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.RequisicaoObra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequisicaoObraDTO {
    private Long id;
    private String data;
    private Long idMoradorUnidade;
    private String status;
    private String nomeMorador;
    private String numeroUnidade;

    public static RequisicaoObraDTO create(RequisicaoObra requisicaoObra){
        ModelMapper modelMapper = new ModelMapper();
        RequisicaoObraDTO dto = modelMapper.map(requisicaoObra, RequisicaoObraDTO.class);
        dto.numeroUnidade = requisicaoObra.getMoradorUnidade().getUnidade().getNumero();
        dto.nomeMorador = requisicaoObra.getMoradorUnidade().getMorador().getNome();
        return dto;
    }

}
