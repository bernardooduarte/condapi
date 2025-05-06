package com.example.condapi.api.dto;

import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.RequisicaoObra;
import org.modelmapper.ModelMapper;

public class RequisicaoObraDTO {
    private Long id;
    private String data;
    private Long idMorador;
    private Long idUnidade;
    private String status;
    private String nomeMorador;
    private String numeroUnidade;

    public static RequisicaoObraDTO create(RequisicaoObra requisicaoObra){
        ModelMapper modelMapper = new ModelMapper();
        RequisicaoObraDTO dto = modelMapper.map(requisicaoObra, RequisicaoObraDTO.class);
        dto.numeroUnidade = requisicaoObra.getUnidade().getNumero();
        dto.nomeMorador = requisicaoObra.getMorador().getNome();
        return dto;
    }
}
