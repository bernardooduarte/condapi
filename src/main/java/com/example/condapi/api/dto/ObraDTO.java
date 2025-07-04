package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Obra;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.RequisicaoObra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ObraDTO {

    private Long id;

    private String descricao;
    private String dataInicio;
    private String dataFim;
    private Long idPrestadorServico;
    private Long idRequisicaoObra;
    private String nomePrestadorServico;
    private String dataRequisicaoObra;

    public static ObraDTO create(Obra obra){
        ModelMapper modelMapper = new ModelMapper();
        ObraDTO dto = modelMapper.map(obra, ObraDTO.class);
        dto.nomePrestadorServico = obra.getPrestadorServico().getNome();
        dto.dataRequisicaoObra = obra.getRequisicaoObra().getData();

        return dto;
    }
}
