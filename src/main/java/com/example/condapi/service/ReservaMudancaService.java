package com.example.condapi.service;

import com.example.condapi.exception.RegraNegocioException;
import com.example.condapi.model.entity.Reserva;
import com.example.condapi.model.entity.ReservaMudanca;
import com.example.condapi.model.repository.ReservaMudancaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservaMudancaService {
    public ReservaMudancaRepository repository;

    public ReservaMudancaService(ReservaMudancaRepository repository){
        this.repository = repository;
    }

    public List<ReservaMudanca> getReservaMudancaRepository(){
        return repository.findAll();
    }

    public Optional<ReservaMudanca> getReservaMudancaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public ReservaMudanca salvar(ReservaMudanca reservaMudanca){
        validar(reservaMudanca);
        return repository.save(reservaMudanca);
    }

    @Transactional
    public void excluir(ReservaMudanca reservaMudanca){
        Objects.requireNonNull(reservaMudanca.getId());
        repository.delete(reservaMudanca);
    }
        public void validar(ReservaMudanca reservaMudanca){
        if(reservaMudanca.getHoraInicio() == null || reservaMudanca.getHoraInicio().trim().equals("")){
            throw new RegraNegocioException("Hora de início inválido");
        }
        if(reservaMudanca.getHoraFim() == null || reservaMudanca.getHoraFim().trim().equals("")){
            throw new RegraNegocioException("Hora de fim inválido");
        }
        if(reservaMudanca.getData() == null || reservaMudanca.getData().trim().equals("")){
            throw new RegraNegocioException("Data inválida");
        }
    }
}
