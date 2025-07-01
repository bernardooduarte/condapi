package com.example.condapi.api.dto;

import com.example.condapi.model.entity.MoradorUnidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoradorUnidadeDTO {

    private Long id;

    private Long idMorador;
    private String nomeMorador;
    private String cpfMorador;

    private Long idUnidade;
    private String numeroUnidade;
    private String blocoUnidade;

    private String tipoDeVinculo;
    private String dataInicioMoradia;
    private boolean isResponsavelFinanceiro;

    public static MoradorUnidadeDTO create(MoradorUnidade moradorUnidade) {
        ModelMapper modelMapper = new ModelMapper();
        MoradorUnidadeDTO dto = modelMapper.map(moradorUnidade, MoradorUnidadeDTO.class);

        if (moradorUnidade.getMorador() != null) {
            dto.idMorador = moradorUnidade.getMorador().getId();
            dto.nomeMorador = moradorUnidade.getMorador().getNome();
            dto.cpfMorador = moradorUnidade.getMorador().getCpf();
        }

        if (moradorUnidade.getUnidade() != null) {
            dto.idUnidade = moradorUnidade.getUnidade().getId();
            dto.numeroUnidade = moradorUnidade.getUnidade().getNumero();
            if (moradorUnidade.getUnidade().getBloco() != null) {
                dto.blocoUnidade = moradorUnidade.getUnidade().getBloco().getNome();
            }
        }

        if (moradorUnidade.getDataInicioMoradia() != null) {
            dto.dataInicioMoradia = moradorUnidade.getDataInicioMoradia();
        }

        return dto;
    }

}
