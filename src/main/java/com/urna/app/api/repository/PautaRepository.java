package com.urna.app.api.repository;

import com.urna.app.api.persistence.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<PautaEntity, Long> {
}
