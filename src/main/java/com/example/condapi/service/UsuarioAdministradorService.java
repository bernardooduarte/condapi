package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.UsuarioAdministrador;
import com.example.condapi.model.repository.UsuarioAdministradorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioAdministradorService {
    public UsuarioAdministradorRepository repository;

    public UsuarioAdministradorService(UsuarioAdministradorRepository repository){
        this.repository = repository;
    }

    public List<UsuarioAdministrador> getUsuarioAdministrador(){
        return repository.findAll();
    }

    public Optional<UsuarioAdministrador> getUsuarioAdministradorById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public UsuarioAdministrador salvar(UsuarioAdministrador usuarioAdministrador){
        validar(usuarioAdministrador);
        return repository.save(usuarioAdministrador);
    }

    @Transactional
    public void excluir(UsuarioAdministrador usuarioAdministrador){
        Objects.requireNonNull(usuarioAdministrador.getId());
        repository.delete(usuarioAdministrador);
    }

    public void validar(UsuarioAdministrador usuarioAdministrador){
        if(usuarioAdministrador.getNomeEmpresa() == null || usuarioAdministrador.getNomeEmpresa().trim().equals("")){
            throw new RegraNegocioException("Nome da empresa inválido");
        }
        if(usuarioAdministrador.getCnpj() == null || usuarioAdministrador.getCnpj().trim().equals("")){
            throw new RegraNegocioException("CNPJ inválido");
        }
        if(usuarioAdministrador.getNomeResponsavelComercial() == null || usuarioAdministrador.getNomeResponsavelComercial().trim().equals("")){
            throw new RegraNegocioException("Nome do responsável comercial inválido");
        }
        if(usuarioAdministrador.getTelefoneComercial() == null || usuarioAdministrador.getTelefoneComercial().trim().equals("")){
            throw new RegraNegocioException("Telefone comercial inválido");
        }
        if(usuarioAdministrador.getLogradouro() == null || usuarioAdministrador.getLogradouro().trim().equals("")){
            throw new RegraNegocioException("Logradouro inválido");
        }
        if(usuarioAdministrador.getNumero() == null || usuarioAdministrador.getNumero() == 0){
            throw new RegraNegocioException("Número inválido");
        }
        if(usuarioAdministrador.getComplemento() == null || usuarioAdministrador.getComplemento().trim().equals("")){
            throw new RegraNegocioException("Complemento inválido");
        }
        if(usuarioAdministrador.getBairro() == null || usuarioAdministrador.getBairro().trim().equals("")){
            throw new RegraNegocioException("Bairro inválido");
        }
        if(usuarioAdministrador.getUf() == null || usuarioAdministrador.getUf().trim().equals("")){
            throw new RegraNegocioException("UF inválido");
        }
        if(usuarioAdministrador.getCep() == null || usuarioAdministrador.getCep().trim().equals("")){
            throw new RegraNegocioException("CEP inválido");
        }

    }
}
