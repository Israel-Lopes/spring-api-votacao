package com.urna.app.api.v1.repository;

import com.urna.app.api.v1.persistence.entity.FormularioDeVotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormularioDeVotoRepository extends JpaRepository<FormularioDeVotoEntity, Long> {
}