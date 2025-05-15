package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Funcionario;
import com.example.condapi.model.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {
    public FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository){
        this.repository = repository;
    }

    public List<Funcionario> getFuncionarioRepository(){
        return repository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario){
        validar(funcionario);
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario){
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    public void validar(Funcionario funcionario){
        if(funcionario.getNome() == null || funcionario.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if(funcionario.getCpf() == null || funcionario.getCpf().trim().equals("")){
            throw new RegraNegocioException("CPF inválido");
        }
        if(funcionario.getEmpresa() == null || funcionario.getEmpresa().trim().equals("")){
            throw new RegraNegocioException("Empresa inválida");
        }
        if(funcionario.getCelularComercial() == null || funcionario.getCelularComercial().trim().equals("")){
            throw new RegraNegocioException("Celular Comercial inválido");
        }
        if(funcionario.getEmail() == null || funcionario.getEmail().trim().equals("")){
            throw new RegraNegocioException("Email inválido");
        }
    }
}
