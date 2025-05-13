package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Usuario;
import com.example.condapi.model.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {
    public UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }

    public List<Usuario> getUsuario(){
        return repository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Usuario salvar(Usuario usuario){
        validar(usuario);
        return repository.save(usuario);
    }

    @Transactional
    public void excluir(Usuario usuario){
        Objects.requireNonNull(usuario.getId());
        repository.delete(usuario);
    }

    public void validar(Usuario usuario){
        if(usuario.getEmail() == null || usuario.getEmail().trim().equals("")){
            throw new RegraNegocioException("E-mail inválido");
        }
        if(usuario.getCpf() == null || usuario.getCpf().trim().equals("")){
            throw new RegraNegocioException("CPF inválido");
        }
        if(usuario.getSenha() == null || usuario.getSenha().trim().equals("")){
            throw new RegraNegocioException("Senha inválida");
        }
    }
}
