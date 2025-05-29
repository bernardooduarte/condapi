package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Morador;
import com.example.condapi.model.repository.MoradorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MoradorService {
    public MoradorRepository repository;

    public MoradorService(MoradorRepository repository){
        this.repository = repository;
    }

    public List<Morador> getMoradorRepository(){
        return repository.findAll();
    }

    public Optional<Morador> getMoradorById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Morador salvar(Morador morador){
        validar(morador);
        return repository.save(morador);
    }

    @Transactional
    public void excluir(Morador morador){
        Objects.requireNonNull(morador.getId());
        repository.delete(morador);
    }
    public void validar(Morador morador) {
        if (morador.getNome() == null || morador.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (morador.getCpf() == null || morador.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
        if (morador.getCelularPessoal() == null || morador.getCelularPessoal().trim().equals("")) {
            throw new RegraNegocioException("Celular pessoal inválido");
        }
        if(morador.getCelularComercial() == null || morador.getCelularComercial().trim().equals("")){
            throw new RegraNegocioException("Celular comercial inválido");
        }
        if(morador.getEmail() == null || morador.getEmail().trim().equals("")){
            throw new RegraNegocioException("E-mail inválido");
        }
        if(morador.getStatusProprietario() == null || morador.getStatusProprietario().trim().equals("")){
            throw new RegraNegocioException("E-mail inválido");
        }
    }

    public List<Morador> getMoradores() {
        return repository.findAll();
    }
}
