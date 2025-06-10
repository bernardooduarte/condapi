package com.example.condapi.service;

import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.repository.PrestadorServicoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PrestadorServicoService {

    public PrestadorServicoRepository repository;

    public PrestadorServicoService (PrestadorServicoRepository repository){
        this.repository = repository;
    }

    public List<PrestadorServico> getPrestadorServicoRepository(){
        return repository.findAll();
    }

    public Optional<PrestadorServico> getPrestadorServicoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public PrestadorServico salvar(PrestadorServico prestadorServico){
        validar(prestadorServico);
        return repository.save(prestadorServico);
    }

    @Transactional
    public void excluir(PrestadorServico prestadorServico){
        Objects.requireNonNull(prestadorServico.getId());
        repository.delete(prestadorServico);
    }

    public void validar(PrestadorServico prestadorServico){

    }

    public List<PrestadorServico> getPrestadoresServico() {
        return repository.findAll();
    }
}
