package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.model.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UnidadeService {
    public UnidadeRepository repository;

    public UnidadeService(UnidadeRepository repository){
        this.repository = repository;
    }

    public List<Unidade> getUnidadeRepository(){
        return repository.findAll();
    }

    public Optional<Unidade> getUnidadeById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Unidade salvar(Unidade unidade){
        validar(unidade);
        return repository.save(unidade);
    }

    @Transactional
    public void excluir(Unidade unidade){
        Objects.requireNonNull(unidade.getId());
        repository.delete(unidade);
    }

    public void validar(Unidade unidade){
        if(unidade.getNumero() == null || unidade.getNumero().trim().equals("")){
            throw new RegraNegocioException("Número inválido");
        }
        if(unidade.getRua() == null || unidade.getRua().trim().equals("")){
            throw new RegraNegocioException("Rua inválida");
        }
        if(unidade.getPetFriendly() == null || unidade.getPetFriendly().trim().equals("")){
            throw new RegraNegocioException("Condição de Pet Friendly inválido");
        }
        if(unidade.getQuantidadeVagas() == null || unidade.getQuantidadeVagas().trim().equals("")){
            throw new RegraNegocioException("Quantidade de vagas inválida");
        }
    }

    public List<Unidade> getUnidades() {
        return repository.findAll();
    }
}