package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.*;
import com.example.condapi.model.repository.CondominioRepository;
import com.example.condapi.model.repository.EncomendaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EncomendaService {
    public EncomendaRepository repository;

    public Optional<Encomenda> getEncomendaById(Long id){
        return repository.findById(id);
    }

    public EncomendaService(EncomendaRepository repository){
        this.repository = repository;
    }

    public List<Encomenda> getEncomendas() {
        return repository.findAll();
    }

    public List<Encomenda> getEncomendaByPorteiro(Optional<Porteiro> porteiro) {
        return repository.findByPorteiro(porteiro);
    }

    public List<Encomenda> getEncomendaByMoradorUnidade(Optional<MoradorUnidade> moradorUnidade) {
        return repository.findByMoradorUnidade(moradorUnidade);
    }

    @Transactional
    public Encomenda salvar(Encomenda encomenda){
        validar(encomenda);
        return repository.save(encomenda);
    }

    @Transactional
    public void excluir(Encomenda encomenda){
        Objects.requireNonNull(encomenda.getId());
        repository.delete(encomenda);
    }

    public void validar(Encomenda encomenda ){
        if (encomenda.getMoradorUnidade() == null || encomenda.getMoradorUnidade().getId() == null || encomenda.getMoradorUnidade().getId() == 0) {
            throw new RegraNegocioException("Associação Morador/Unidade inválida");
        }
        if (encomenda.getPorteiro() == null || encomenda.getPorteiro().getId() == null || encomenda.getPorteiro().getId() == 0) {
            throw new RegraNegocioException("Porteiro inválido");
        }
        if(encomenda.getData() == null || encomenda.getData().trim().equals("")){
            throw new RegraNegocioException("Data inválido");
        }
        if(encomenda.getHora() == null || encomenda.getHora().trim().equals("")) {
            throw new RegraNegocioException("Hora inválida");
        }
    }

}
