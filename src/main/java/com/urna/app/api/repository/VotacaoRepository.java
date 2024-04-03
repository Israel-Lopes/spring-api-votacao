package com.urna.app.api.repository;

import com.urna.app.api.persistence.entity.SessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoRepository extends JpaRepository<SessaoEntity, Long> {
}
