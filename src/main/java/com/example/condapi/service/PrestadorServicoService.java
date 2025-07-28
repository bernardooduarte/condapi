package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.Unidade;
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

    public List<PrestadorServico> getPrestadorServicoByUnidade( Optional <Unidade> unidade){
        return repository.findByUnidade(unidade);
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
        if (prestadorServico.getUnidade() == null || prestadorServico.getUnidade().getId() == null || prestadorServico.getUnidade().getId() == 0) {
            throw new RegraNegocioException("Unidade inválido");
        }
        if(prestadorServico.getNome() == null || prestadorServico.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if(prestadorServico.getCpf() == null || prestadorServico.getCpf().trim().equals("")){
            throw new RegraNegocioException("CPF inválido");
        }
        if(prestadorServico.getEmpresa() == null || prestadorServico.getEmpresa().trim().equals("")){
            throw new RegraNegocioException("Empresa inválida");
        }
        if(prestadorServico.getCelularComercial() == null || prestadorServico.getCelularComercial().trim().equals("")){
            throw new RegraNegocioException("Celular Comercial inválido");
        }
        if(prestadorServico.getEmail() == null || prestadorServico.getEmail().trim().equals("")){
            throw new RegraNegocioException("Email inválido");
        }
        if(prestadorServico.getUnidade() == null || prestadorServico.getUnidade().getId() == null || prestadorServico.getUnidade().getId() == 0){
            throw new RegraNegocioException("Unidade inválida");
        }
        if(prestadorServico.getCondominio() == null || prestadorServico.getCondominio().getId() == null || prestadorServico.getCondominio().getId() == 0){
            throw new RegraNegocioException("Condomínio inválido");
        }
    }

    public List<PrestadorServico> getPrestadoresServico() {
        return repository.findAll();
    }
}
