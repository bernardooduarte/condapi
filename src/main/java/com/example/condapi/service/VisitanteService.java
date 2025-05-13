package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Visitante;
import com.example.condapi.model.repository.VisitanteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VisitanteService {

    public VisitanteRepository repository;

    public VisitanteService(VisitanteRepository repository){
        this.repository = repository;
    }

    public List<Visitante> getVisitante(){
        return repository.findAll();
    }

    public Optional<Visitante> getVisitanteById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Visitante salvar(Visitante visitante){
        validar(visitante);
        return repository.save(visitante);
    }

    @Transactional
    public void excluir(Visitante visitante){
        Objects.requireNonNull(visitante.getId());
        repository.delete(visitante);
    }

    public void validar(Visitante visitante){
        if(visitante.getPlaca() == null || visitante.getPlaca().trim().equals("")){
            throw new RegraNegocioException("Placa inválida");
        }
        if(visitante.getNome() == null || visitante.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
    }
}
