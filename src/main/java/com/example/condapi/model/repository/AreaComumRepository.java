package com.example.condapi.model.repository;

import com.example.condapi.model.entity.AreaComum;
import com.example.condapi.model.entity.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AreaComumRepository extends JpaRepository <AreaComum,Long>{

    List<AreaComum> findByCondominio(Optional<Condominio> condominio);
}
