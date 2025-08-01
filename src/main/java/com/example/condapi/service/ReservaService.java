package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.MoradorUnidade;
import com.example.condapi.model.entity.PrestadorServico;
import com.example.condapi.model.entity.Reserva;
import com.example.condapi.model.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservaService {
    public ReservaRepository repository;

    public ReservaService(ReservaRepository repository){
        this.repository = repository;
    }

    public List<Reserva> getReservaRepository(){
        return repository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id){
        return repository.findById(id);
    }

    public List<Reserva> getReservas() {
        return repository.findAll();
    }

    public List<Reserva> getReservaByMoradorUnidade(Optional<MoradorUnidade> moradorUnidade) {
        return repository.findByMoradorUnidade(moradorUnidade);
    }

    public List<Reserva> getReservaByPrestadorServico(Optional<PrestadorServico> prestadorServico) {
        return repository.findByPrestadorServico(prestadorServico);
    }

    @Transactional
    public Reserva salvar(Reserva reserva){
        validar(reserva);
        return repository.save(reserva);
    }

    @Transactional
    public void excluir(Reserva reserva){
        Objects.requireNonNull(reserva.getId());
        repository.delete(reserva);
    }

    public void validar(Reserva reserva){
        if (reserva.getMoradorUnidade() == null || reserva.getMoradorUnidade().getId() == null || reserva.getMoradorUnidade().getId() == 0) {
            throw new RegraNegocioException("Associação Morador/Unidade inválida");
        }
        if (reserva.getPrestadorServico() == null || reserva.getPrestadorServico().getId() == null || reserva.getPrestadorServico().getId() == 0) {
            throw new RegraNegocioException("Prestador de Serviço inválido");
        }
        if(reserva.getHoraInicio() == null || reserva.getHoraInicio().trim().equals("")){
            throw new RegraNegocioException("Hora de início inválido");
        }
        if(reserva.getHoraFim() == null || reserva.getHoraFim().trim().equals("")){
            throw new RegraNegocioException("Hora de fim inválido");
        }
        if(reserva.getData() == null || reserva.getData().trim().equals("")){
            throw new RegraNegocioException("Data inválida");
        }
    }
}
