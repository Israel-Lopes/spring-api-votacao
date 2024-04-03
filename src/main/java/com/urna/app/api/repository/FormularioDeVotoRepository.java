package com.urna.app.api.repository;

import com.urna.app.api.persistence.entity.FormularioDeVotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormularioDeVotoRepository extends JpaRepository<FormularioDeVotoEntity, Long> {
}
