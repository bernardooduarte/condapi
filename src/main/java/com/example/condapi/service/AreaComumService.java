package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.AreaComum;
import com.example.condapi.model.entity.Condominio;
import com.example.condapi.model.repository.AreaComumRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AreaComumService {
    public AreaComumRepository repository;

    public AreaComumService(AreaComumRepository repository){
        this.repository = repository;
    }

    public List<AreaComum> getAreaComumRepository(){
        return repository.findAll();
    }

    public Optional<AreaComum> getAreaComumById(Long id){
        return repository.findById(id);
    }

    public List<AreaComum> getAreacomumByCondominio(Optional<Condominio> condominio) {
        return repository.findByCondominio(condominio);
    }

    @Transactional
    public AreaComum salvar(AreaComum areaComum){
        validar(areaComum);
        return repository.save(areaComum);
    }

    @Transactional
    public void excluir(AreaComum areaComum){
        Objects.requireNonNull(areaComum.getId());
        repository.delete(areaComum);
    }

    public void validar(AreaComum areaComum ){
        if (areaComum.getCondominio() == null || areaComum.getCondominio().getId() == null || areaComum.getCondominio().getId() == 0) {
            throw new RegraNegocioException("Condomínio inválido");
        }
        if(areaComum.getNome() == null || areaComum.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if(areaComum.getDescricao() == null || areaComum.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Descrição inválida");
        }
        if(areaComum.getCapacidadeMax() == null || areaComum.getCapacidadeMax() == (0)){
            throw new RegraNegocioException("Capacidade Max inválida");
        }
        if(areaComum.getHorarioUtilizacaoInicio() == null || areaComum.getHorarioUtilizacaoInicio().trim().equals("")){
            throw new RegraNegocioException("Hora inicio inválida");
        }
        if(areaComum.getHorarioUtilizacaoFim() == null || areaComum.getHorarioUtilizacaoFim().trim().equals("")){
            throw new RegraNegocioException("Hora Fim inválida");
        }
        if(areaComum.getRestricoes() == null || areaComum.getRestricoes().trim().equals("")){
            throw new RegraNegocioException("Restricoes inválida");
        }

    }

    public List<AreaComum> getAreasComuns() {
        return repository.findAll();
    }
}
