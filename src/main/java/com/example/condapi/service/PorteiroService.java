package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Porteiro;
import com.example.condapi.model.repository.PorteiroRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PorteiroService {
    public PorteiroRepository repository;

    public PorteiroService(PorteiroRepository repository){
        this.repository = repository;
    }

    public List<Porteiro> getPorteiroRepository(){
        return repository.findAll();
    }

    public Optional<Porteiro> getPorteiroById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Porteiro salvar(Porteiro porteiro){
        validar(porteiro);
        return repository.save(porteiro);
    }

    @Transactional
    public void excluir(Porteiro porteiro){
        Objects.requireNonNull(porteiro.getId());
        repository.delete(porteiro);
    }

    public void validar(Porteiro porteiro){
        if(porteiro.getNome() == null || porteiro.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
    }

    public List<Porteiro> getPorteiros() {
        return repository.findAll();
    }
}
