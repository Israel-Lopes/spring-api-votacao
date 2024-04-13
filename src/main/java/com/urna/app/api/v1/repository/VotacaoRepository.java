package com.urna.app.api.v1.repository;

import com.urna.app.api.v1.persistence.entity.SessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoRepository extends JpaRepository<SessaoEntity, Long> {
}