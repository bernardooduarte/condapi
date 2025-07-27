package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.model.entity.Funcionario;
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

    public List<Porteiro> getPorteiroByCondominio(Optional<Condominio> condominio) {
        return repository.findByCondominio(condominio);
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
        if (porteiro.getCondominio() == null || porteiro.getCondominio().getId() == null || porteiro.getCondominio().getId() == 0) {
            throw new RegraNegocioException("Condomínioo inválido");
        }
        if(porteiro.getNome() == null || porteiro.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if(porteiro.getCpf() == null || porteiro.getCpf().trim().equals("")){
            throw new RegraNegocioException("CPF inválido");
        }
        if(porteiro.getEmpresa() == null || porteiro.getEmpresa().trim().equals("")){
            throw new RegraNegocioException("Empresa inválida");
        }
        if(porteiro.getCelularComercial() == null || porteiro.getCelularComercial().trim().equals("")){
            throw new RegraNegocioException("Celular Comercial inválido");
        }
        if(porteiro.getEmail() == null || porteiro.getEmail().trim().equals("")){
            throw new RegraNegocioException("Email inválido");
        }
        if(porteiro.getCondominio() == null || porteiro.getCondominio().getId() == null || porteiro.getCondominio().getId() == 0){
            throw new RegraNegocioException("Condomínio inválido.");
        }
    }

    public List<Porteiro> getPorteiros() {
        return repository.findAll();
    }
}
