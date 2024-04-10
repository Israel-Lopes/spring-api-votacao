package com.urna.app.api.service;

import com.urna.app.api.persistence.entity.PautaEntity;
import com.urna.app.api.repository.PautaRepository;
import com.urna.app.api.web.dto.Associado;
import com.urna.app.api.service.in.IPauta;
import com.urna.app.api.web.dto.Pauta;
import com.urna.app.api.web.mapper.PautaMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PautaImpl implements IPauta {

    @Autowired(required = true)
    private PautaRepository repository;
    private static final Logger logger = LogManager.getLogger(Associado.class);
    public ResponseEntity getPauta(HttpServletRequest request, Long id) {
        try {
            Optional<PautaEntity> entity = repository.findById(id);
            return entity.isPresent()
                    ? ResponseEntity.ok().header("Content-Type", "application/json")
                        .body(PautaMapper.unmarshall(entity.get()))
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<List<Pauta>> getPautaList(HttpServletRequest request) {
        try {
            List<PautaEntity> entities = repository.findAll();
            List<Pauta> pautas = new ArrayList<>(PautaMapper.unmarshall(entities.stream().toList()));
            return pautas != null
                    ? ResponseEntity.ok().header("Content-Type", "application/json").body(pautas)
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity createPauta(Pauta model) {
        try {
            PautaEntity entity = repository.save(PautaMapper.marshall(model));
            if (entity != null) {
                logger.info("Pauta criada com sucesso! ID: {}", entity.getId());
                return ResponseEntity.ok().header("Content-Type", "application/json")
                        .body(PautaMapper.unmarshall(entity));
            }
            logger.error("Erro ao criar Pauta: entidade retornada e nula");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao criar Pauta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity updatePauta(Pauta model) {
        try {
            Optional<PautaEntity> optionalEntity = repository.findById(model.getId());
            if (optionalEntity.isPresent()) {
                PautaEntity entity = optionalEntity.get();
                entity.setTitulo(model.getTitulo());
                repository.save(entity);
                logger.info("Pauta atualizada com sucesso! ID: {}", entity.getId());
                return ResponseEntity.ok()
                        .header("Content-Type", "application/json")
                        .body(PautaMapper.unmarshall(entity));
            }
            logger.error("Erro ao atualizar pauta: entidade retornada e nula");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar pauta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity deletePauta(Long id) {
        try {
            return repository.findById(id).map(record -> {
                repository.deleteById(id);
                return ResponseEntity.ok().header("Content-Type", "application/json").body(id);
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}