package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.MoradorUnidade;
import com.example.condapi.model.repository.MoradorUnidadeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MoradorUnidadeService {

    public MoradorUnidadeRepository repository;

    public MoradorUnidadeService(MoradorUnidadeRepository repository) {
        this.repository = repository;
    }

    public List<MoradorUnidade> getMoradorUnidadeRepository() {
        return repository.findAll();
    }

    public Optional<MoradorUnidade> getMoradorUnidadeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public MoradorUnidade salvar(MoradorUnidade moradorUnidade) {
        validar(moradorUnidade);
        return repository.save(moradorUnidade);
    }

    @Transactional
    public void excluir(MoradorUnidade moradorUnidade) {
        Objects.requireNonNull(moradorUnidade.getId());
        repository.delete(moradorUnidade);
    }

    public void validar(MoradorUnidade moradorUnidade) {
        if (moradorUnidade.getMorador() == null || moradorUnidade.getMorador().getId() == null) {
            throw new RegraNegocioException("Morador inválido.");
        }
        if (moradorUnidade.getUnidade() == null || moradorUnidade.getUnidade().getId() == null) {
            throw new RegraNegocioException("Unidade inválida.");
        }
        if (moradorUnidade.getTipoDeVinculo() == null || moradorUnidade.getTipoDeVinculo().trim().equals("")) {
            throw new RegraNegocioException("O tipo de vínculo é obrigatório.");
        }

        if (moradorUnidade.getDataInicioMoradia() == null) {
            throw new RegraNegocioException("A data de início da moradia é obrigatória.");
        }
    }

    public List<MoradorUnidade> getMoradorUnidades() {
        return repository.findAll();
    }
}
