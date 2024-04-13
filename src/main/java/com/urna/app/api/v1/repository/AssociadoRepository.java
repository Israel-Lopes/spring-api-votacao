package com.urna.app.api.v1.repository;

import com.urna.app.api.v1.persistence.entity.AssociadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends JpaRepository<AssociadoEntity, Long> {
    AssociadoEntity findByCpf(String cpf);
}