package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Encomenda;
import com.example.condapi.model.entity.MoradorUnidade;
import com.example.condapi.model.entity.Unidade;
import com.example.condapi.model.entity.Veiculo;
import com.example.condapi.model.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VeiculoService {
    public VeiculoRepository repository;

    public VeiculoService(VeiculoRepository repository){
        this.repository = repository;
    }

    public List<Veiculo> getVeiculos(){
        return repository.findAll();
    }

    public Optional<Veiculo> getVeiculoById(Long id){
        return repository.findById(id);
    }

    public List<Veiculo> getVeiculoByMoradorUnidade(Optional<MoradorUnidade> moradorUnidade) {
        return repository.findByMoradorUnidade(moradorUnidade);
    }

    @Transactional
    public Veiculo salvar(Veiculo veiculo){
        validar(veiculo);
        return repository.save(veiculo);
    }

    @Transactional
    public void excluir(Veiculo veiculo){
        Objects.requireNonNull(veiculo.getId());
        repository.delete(veiculo);
    }

    public List<Veiculo> getVeiculosByMoradorUnidade(Optional<MoradorUnidade> moradorUnidade) {
        return repository.findByMoradorUnidade(moradorUnidade);
    }

    public void validar(Veiculo veiculo){
        if (veiculo.getMoradorUnidade() == null || veiculo.getMoradorUnidade().getId() == null || veiculo.getMoradorUnidade().getId() == 0) {
            throw new RegraNegocioException("Não encontrado inválido");
        }
        if(veiculo.getPlaca() == null || veiculo.getPlaca().trim().equals("")){
            throw new RegraNegocioException("Placa inválida");
        }
        if(veiculo.getMarca() == null || veiculo.getMarca().trim().equals("")){
            throw new RegraNegocioException("Marca inválida");
        }
        if(veiculo.getModelo() == null || veiculo.getModelo().trim().equals("")){
            throw new RegraNegocioException("Modelo inválido");
        }
        if(veiculo.getCor() == null || veiculo.getCor().trim().equals("")){
            throw new RegraNegocioException("Cor inválido");
        }
        if(veiculo.getTipoVeiculo() == null || veiculo.getTipoVeiculo().trim().equals("")) {
            throw new RegraNegocioException("Tipo de veículo inválido");
        }
        if(veiculo.getMoradorUnidade() == null || veiculo.getMoradorUnidade().getId() == null || veiculo.getMoradorUnidade().getId() == 0){
            throw new RegraNegocioException("Associação Morador/Unidade inválida");
        }
    }
    public List<Veiculo> getVeiculo() {
        return repository.findAll();
    }
}
