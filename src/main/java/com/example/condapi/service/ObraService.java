package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.*;
import com.example.condapi.model.repository.ObraRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ObraService {
    public ObraRepository repository;

    public ObraService(ObraRepository repository){
        this.repository = repository;
    }

    public List<Obra> getObraRepository(){
        return repository.findAll();
    }

    public Optional<Obra> getObraById(Long id){
        return repository.findById(id);
    }

    public List<Obra> getObraByPrestadorServico(Optional<PrestadorServico> prestadorServico) {
        return repository.findByPrestadorServico(prestadorServico);
    }

    public List<Obra> getObraByRequisicaoObra(Optional<RequisicaoObra> requisicaoObra) {
        return repository.findByRequisicaoObra(requisicaoObra);
    }

    @Transactional
    public Obra salvar(Obra obra){
        validar(obra);
        return repository.save(obra);
    }

    @Transactional
    public void excluir(Obra obra){
        Objects.requireNonNull(obra.getId());
        repository.delete(obra);
    }
    public void validar(Obra obra){
        if (obra.getRequisicaoObra() == null || obra.getRequisicaoObra().getId() == null || obra.getRequisicaoObra().getId() == 0) {
            throw new RegraNegocioException("Requisação inválido");
        }
        if (obra.getPrestadorServico() == null || obra.getPrestadorServico().getId() == null || obra.getPrestadorServico().getId() == 0) {
            throw new RegraNegocioException("Prestador Servico inválido");
        }
        if(obra.getDescricao() == null || obra.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Descrição inválida");
        }
        if(obra.getDataInicio() == null || obra.getDataInicio().trim().equals("")){
            throw new RegraNegocioException("Data de início inválida");
        }
        if(obra.getDataFim() == null || obra.getDataFim().trim().equals("")){
            throw new RegraNegocioException("Data de fim inválida");
        }
    }

    public List<Obra> getObras() {
        return repository.findAll();
    }
}
