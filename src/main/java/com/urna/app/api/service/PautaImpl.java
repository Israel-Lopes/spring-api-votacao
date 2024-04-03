package com.urna.app.api.service;

import com.urna.app.api.persistence.entity.PautaEntity;
import com.urna.app.api.repository.PautaRepository;
import com.urna.app.api.service.in.IPauta;
import com.urna.app.api.service.model.Pauta;
import com.urna.app.api.web.mapper.PautaMapper;
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
    PautaRepository repository;
    @Override
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
    @Override
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
    @Override
    public ResponseEntity createPauta(Pauta model) {
        try {
            PautaEntity entity = repository.save(PautaMapper.marshall(model));
            return entity != null
                    ? ResponseEntity.ok().header("Content-Type", "application/json")
                        .body(PautaMapper.unmarshall(entity))
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
    public ResponseEntity updatePauta(Pauta model) {
        try {
            Optional<PautaEntity> optionalEntity = repository.findById(model.getId());
            if (optionalEntity.isPresent()) {
                PautaEntity entity = optionalEntity.get();
                entity.setTitulo(model.getTitulo());
                repository.save(entity);
                return ResponseEntity.ok()
                        .header("Content-Type", "application/json")
                        .body(PautaMapper.unmarshall(entity));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
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
