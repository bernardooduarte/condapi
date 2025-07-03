package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Encomenda;
import com.example.condapi.model.entity.MoradorUnidade;
import com.example.condapi.model.entity.RequisicaoObra;
import com.example.condapi.model.repository.RequisicaoObraRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RequisicaoObraService {
    public RequisicaoObraRepository repository;

    public RequisicaoObraService(RequisicaoObraRepository repository){
        this.repository = repository;
    }

    public List<RequisicaoObra> getRequisicaoObraRepository(){
        return repository.findAll();
    }

    public Optional<RequisicaoObra> getRequisicaoObraById(Long id){
        return repository.findById(id);
    }

    public List<RequisicaoObra> getRequisicaoObraByMoradorUnidade(Optional<MoradorUnidade> moradorUnidade) {
        return repository.findByMoradorUnidade(moradorUnidade);
    }

    @Transactional
    public RequisicaoObra salvar(RequisicaoObra requisicaoObra){
        validar(requisicaoObra);
        return repository.save(requisicaoObra);
    }

    @Transactional
    public void excluir(RequisicaoObra requisicaoObra){
        Objects.requireNonNull(requisicaoObra.getId());
        repository.delete(requisicaoObra);
    }
    public void validar(RequisicaoObra requisicaoObra){
        if (requisicaoObra.getMoradorUnidade() == null || requisicaoObra.getMoradorUnidade().getId() == null || requisicaoObra.getMoradorUnidade().getId() == 0) {
            throw new RegraNegocioException("Não encontrado inválido");
        }
        if(requisicaoObra.getData() == null || requisicaoObra.getData().trim().equals("")){
            throw new RegraNegocioException("Data inválida");
        }
        if(requisicaoObra.getStatus() == null || requisicaoObra.getStatus().trim().equals("")){
            throw new RegraNegocioException("Status inválido");
        }
    }

    public List<RequisicaoObra> getRequisicoesObra() {
        return repository.findAll();
    }
}
