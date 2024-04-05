package com.urna.app.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urna.app.api.service.in.IVotacao;
import com.urna.app.api.service.model.VotoAssociado;
import com.urna.app.client.ValidaCPFClient;
import com.urna.app.client.model.ValidaCPF;
import com.urna.app.api.persistence.entity.AssociadoEntity;
import com.urna.app.api.persistence.entity.FormularioDeVotoEntity;
import com.urna.app.api.persistence.entity.SessaoEntity;
import com.urna.app.api.repository.AssociadoRepository;
import com.urna.app.api.repository.SessaoRepository;
import com.urna.app.api.web.mapper.SessaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VotacaoImpl implements IVotacao {
    private static final String cpfValido = "ABLE_TO_VOTE";
    @Autowired(required = true)
    private AssociadoRepository associadoRepository;
    @Autowired(required = true)
    private SessaoRepository sessaoRepository;
    @Autowired(required=true)
    private ValidaCPFClient client;
    public ResponseEntity createVoto(VotoAssociado model) {
        try {
            String cpfFormatado = model.getCpf()
                    .replace(" ", "")
                    .replace("-", "")
                    .replace(".", "");

            AssociadoEntity entity = associadoRepository.findByCpf(cpfFormatado);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode validaCpfNode = mapper.readTree(client.getValidaCPF(cpfFormatado));

            ValidaCPF validaCPF = new ValidaCPF();
            validaCPF.setStatus(validaCpfNode.get("status").textValue());

            if (!validaCPF.getStatus().equals(cpfValido) || entity == null) {
                return ResponseEntity.notFound().build();
            }

            Optional<SessaoEntity> sessaoOptional = sessaoRepository.findById(model.getIdSessao());

            if (sessaoOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

//            LocalDateTime dataAtual = LocalDateTime.now();
//            if (!sessaoOptional.get().getFimDaContagem().isBefore(dataAtual)) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("A votação para esta sessão foi encerrada.");
//            }

//            List<Long> idDeAssociadosQueJaVotaram = sessaoRepository.getById(sessaoOptional
//                    .get().getId())
//                    .getFormulario()
//                    .getIdAssociadosQueVotaram();
//            Boolean associadoJaVotou = idDeAssociadosQueJaVotaram.stream()
//                    .anyMatch(id -> id.equals(entity.getId()));
//
//            if (associadoJaVotou) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("Voto não computado! Associado ja vou antes!");
//            }

            SessaoEntity sessaoEntity = sessaoOptional.get();

            sessaoEntity.getFormulario().getVotos().add(model.getVoto());
            sessaoEntity.getFormulario().getIdAssociadosQueVotaram().add(entity.getId());
            sessaoRepository.save(sessaoEntity);

            return ResponseEntity.ok().header("Content-Type", "application/json")
                    .body(SessaoMapper.unmarshall(sessaoEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    public ResponseEntity listaTotalVotos(Long idSessao) {
//        try {
//            Optional<SessaoEntity> sessaoOptional = sessaoRepository.findById(idSessao);
//
//            if (sessaoOptional.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            return ResponseEntity.ok().header("Content-Type", "application/json")
//                    .body();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}