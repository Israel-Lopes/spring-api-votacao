package com.urna.app.api.service;

import com.urna.app.api.persistence.entity.AssociadoEntity;
import com.urna.app.api.repository.AssociadoRepository;
import com.urna.app.api.service.in.IAssociado;
import com.urna.app.api.service.model.Associado;
import com.urna.app.api.web.mapper.AssociadoMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssociadoImpl implements IAssociado {
    @Autowired(required = true)
    private AssociadoRepository repository;
    private static final Logger logger = LogManager.getLogger(Associado.class);
    public ResponseEntity getAssociado(HttpServletRequest request, Long id) {
        try {
            Optional<AssociadoEntity> entity = repository.findById(id);
            return entity.isPresent()
                    ? ResponseEntity.ok().header("Content-Type", "application/json")
                        .body(AssociadoMapper.unmarshall(entity.get()))
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<List<Associado>> getAssociadoList(HttpServletRequest request) {
        try {
            List<AssociadoEntity> entities = repository.findAll();
            List<Associado> associados = new ArrayList<>(AssociadoMapper.unmarshall(entities.stream().toList()));
            return associados != null
                    ? ResponseEntity.ok().header("Content-Type", "application/json").body(associados)
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity createAssociado(@RequestBody Associado model) {
        try {
            String cpfFormatado = model.getCpf()
                    .replace(" ", "")
                    .replace("-", "")
                    .replace(".", "");
            model.setCpf(cpfFormatado);
            AssociadoEntity entity = repository.save(AssociadoMapper.marshall(model));
            return entity != null
                    ? ResponseEntity.ok().header("Content-Type", "application/json")
                    .body(AssociadoMapper.unmarshall(entity))
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity updateAssociado(Associado model) {
        try {
            Optional<AssociadoEntity> optionalEntity = repository.findById(model.getId());
            if (optionalEntity.isPresent()) {
                AssociadoEntity entity = optionalEntity.get();
                entity.setCpf(model.getCpf());
                repository.save(entity);
                return ResponseEntity.ok()
                        .header("Content-Type", "application/json")
                        .body(AssociadoMapper.unmarshall(entity));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity deleteAssociado(Long id) {
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