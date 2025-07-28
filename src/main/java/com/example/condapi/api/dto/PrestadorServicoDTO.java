package com.example.condapi.api.dto;

import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.model.entity.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PrestadorServicoDTO {

    private Long id;
    private Long idUnidade;
    private String numeroUnidade;
    private String nome;
    private String cpf;
    private String empresa;
    private String celularComercial;
    private String email;
    private Long idCondominio;
    private String nomeCondominio;

    public static PrestadorServicoDTO create(PrestadorServico prestadorServico){
        ModelMapper modelMapper = new ModelMapper();
        PrestadorServicoDTO dto = modelMapper.map(prestadorServico, PrestadorServicoDTO.class);
        dto.numeroUnidade = prestadorServico.getUnidade().getNumero();
        dto.nomeCondominio = prestadorServico.getCondominio().getNome();
        return dto;
    }
}
