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

import java.util.Collections;
import java.util.Optional;

@Service
public class VotacaoImpl implements IVotacao {
    private static final String cpfValido = "ABLE_TO_VOTE";
    @Autowired(required = true)
    AssociadoRepository associadoRepository;
    @Autowired(required = true)
    SessaoRepository sessaoRepository;
    @Autowired(required=true)
    private ValidaCPFClient client;
    @Override
    public ResponseEntity createVoto(Long id, VotoAssociado model) {
        try {
            String cpfFormatado = model.getCpf().replace(" ", "").replace("-", "");
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

            if (!sessaoOptional.get().getVotacaoEmAndamento()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A votação para esta sessão foi encerrada.");
            }

            SessaoEntity sessaoEntity = sessaoOptional.get();

            FormularioDeVotoEntity formularioDeVoto = new FormularioDeVotoEntity();
            formularioDeVoto.setIdAssociadosQueVotaram(Collections.singletonList(entity.getId()));
            formularioDeVoto.setVotos(Collections.singletonList(model.getVoto()));

            sessaoEntity.getFormulario().getVotos().addAll(formularioDeVoto.getVotos());
            sessaoEntity.getFormulario().getIdAssociadosQueVotaram().addAll(formularioDeVoto.getIdAssociadosQueVotaram());
            sessaoRepository.save(sessaoEntity);

            return ResponseEntity.ok().header("Content-Type", "application/json")
                    .body(SessaoMapper.unmarshall(sessaoEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}