package com.example.condapi.api.dto;

import com.example.condapi.model.entity.Bloco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlocoDTO {

    private Long id;
    private Long idCondominio;
    private String logradouro;
    private String nome;
    private String nomeCondominio;

    public static BlocoDTO create(Bloco bloco){
        ModelMapper modelMapper = new ModelMapper();
        BlocoDTO dto = modelMapper.map(bloco, BlocoDTO.class);

        // Adicionar verificação de nulidade para Condominio
        if (bloco.getCondominio() != null) {
            dto.nomeCondominio = bloco.getCondominio().getNome();
        } else {
            dto.nomeCondominio = null; // Ou "" ou algum valor padrão se Condominio for null
        }

        // Se você também precisa do ID do Condomínio no DTO
        if (bloco.getCondominio() != null) {
            dto.idCondominio = bloco.getCondominio().getId();
        } else {
            dto.idCondominio = null;
        }

        return dto;
    }
}