package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.model.repository.CondominioRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CondominioService {
    public CondominioRepository repository;

    public CondominioService(CondominioRepository repository){
        this.repository = repository;
    }

    public List<Condominio> getCondominioRepository(){
        return repository.findAll();
    }

    public Optional<Condominio> getCondominioById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Condominio salvar(Condominio condominio){
        validar(condominio);
        return repository.save(condominio);
    }

    @Transactional
    public void excluir(Condominio condominio){
        Objects.requireNonNull(condominio.getId());
        repository.delete(condominio);
    }
    public void validar(Condominio condominio) {
        if (condominio.getNome() == null || condominio.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (condominio.getTipoCondominio() == null || condominio.getTipoCondominio().trim().equals("")) {
            throw new RegraNegocioException("Tipo de condomínio inválido");
        }
        if (condominio.getQuantidadePortarias() == null || condominio.getQuantidadePortarias() == 0) {
            throw new RegraNegocioException("Quantidade de portarias inválida");
        }
        if(condominio.getQuantidadeUnidades() == null || condominio.getQuantidadeUnidades() == 0){
            throw new RegraNegocioException("Quantidade de unidades inválida");
        }
        if(condominio.getQuantidadeBlocos() == null || condominio.getQuantidadeBlocos() == 0){
            throw new RegraNegocioException("Quantidade de blocos inválida");
        }
        if(condominio.getExigeIdentificacao() == null || condominio.getExigeIdentificacao().trim().equals("")){
            throw new RegraNegocioException("Resposta de exigência de identificação inválida");
        }
        if(condominio.getChaveAcesso() == null || condominio.getChaveAcesso().trim().equals("")){
            throw new RegraNegocioException("Chave de acesso inválida");
        }
        if(condominio.getLogradouro() == null || condominio.getLogradouro().trim().equals("")){
            throw new RegraNegocioException("Logradouro inválido");
        }
        if(condominio.getNumero() == null || condominio.getNumero() == 0){
            throw new RegraNegocioException("Número inválido");
        }
        if(condominio.getComplemento() == null || condominio.getComplemento().trim().equals("")){
            throw new RegraNegocioException("Complemento inválido");
        }
        if(condominio.getBairro() == null || condominio.getBairro().trim().equals("")){
            throw new RegraNegocioException("Bairro inválido");
        }
        if(condominio.getCidade() == null || condominio.getCidade().trim().equals("")){
            throw new RegraNegocioException("Cidade inválida");
        }
        if(condominio.getUf() == null || condominio.getUf().trim().equals("")){
            throw new RegraNegocioException("UF inválido");
        }
        if(condominio.getCep() == null || condominio.getCep().trim().equals("")){
            throw new RegraNegocioException("CEP inválido");
        }
    }

    public List<Condominio> getCondominios() {
        return repository.findAll();
    }
}
