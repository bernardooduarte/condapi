package com.example.condapi.service;
import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Bloco;
<<<<<<< HEAD
=======
import com.example.condapi.model.entity.Bloco;
>>>>>>> 6986545b7bf1f147597964afc1261ccddcb85997
import com.example.condapi.model.repository.BlocoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BlocoService {

    public BlocoRepository repository;

    public BlocoService(BlocoRepository repository){
        this.repository = repository;
    }

    public List<Bloco> getBlocoRepository(){
        return repository.findAll();
    }

    public Optional<Bloco> getBlocoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Bloco salvar(Bloco bloco){
        validar(bloco);
        return repository.save(bloco);
    }

    @Transactional
    public void excluir(Bloco bloco){
        Objects.requireNonNull(bloco.getId());
        repository.delete(bloco);
    }

    public void validar(Bloco bloco ){
        if(bloco.getLogradouro() == null || bloco.getLogradouro().trim().equals("")){
            throw new RegraNegocioException("Logradouro inválido");
        }
        if(bloco.getNome() == null || bloco.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
    }
}
