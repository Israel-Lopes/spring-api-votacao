package com.urna.app.api.v1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urna.app.api.v1.persistence.entity.AssociadoEntity;
import com.urna.app.api.v1.persistence.entity.SessaoEntity;
import com.urna.app.api.v1.repository.AssociadoRepository;
import com.urna.app.api.v1.repository.SessaoRepository;
import com.urna.app.api.v1.service.in.IVotacao;
import com.urna.app.api.v1.utils.FormatarCpf;
import com.urna.app.api.v1.utils.Voto;
import com.urna.app.api.v1.web.dto.Associado;
import com.urna.app.api.v1.web.dto.TotalVotos;
import com.urna.app.api.v1.web.dto.VotoAssociado;
import com.urna.app.api.v1.web.mapper.SessaoMapper;
import com.urna.app.client.ValidaCPFClient;
import com.urna.app.client.model.ValidaCPF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VotacaoImpl implements IVotacao {
    private static final String CPF_VALIDO = "ABLE_TO_VOTE";
    @Autowired(required = true)
    private AssociadoRepository associadoRepository;
    @Autowired(required = true)
    private SessaoRepository sessaoRepository;
    @Autowired(required=true)
    private ValidaCPFClient client;
    @Autowired
    private FormatarCpf formatarCpf;
    private static final Logger logger = LogManager.getLogger(Associado.class);
    public ResponseEntity createVoto(VotoAssociado model) {
        try {
            String cpfFormatado = formatarCpf.replace(model.getCpf());
            AssociadoEntity entity = associadoRepository.findByCpf(cpfFormatado);

            if (entity == null || !validaCPF(cpfFormatado)) {
                logger.error("Erro ao validar entidade ou CPF!");
                return ResponseEntity.notFound().build();
            }

            Optional<SessaoEntity> sessaoOptional = sessaoRepository.findById(model.getIdSessao());
            if (sessaoOptional.isEmpty()) {
                logger.error("Erro ao encontrar sessao!");
                return ResponseEntity.notFound().build();
            }

            LocalDateTime dataAtual = LocalDateTime.now();
            if (!sessaoOptional.get().getFimDaContagem().isAfter(dataAtual)) {
                logger.info("Nao é possivel votar. Sessao encerrada.");
                return ResponseEntity.notFound().build();
            }

            List<Long> idDeAssociadosQueJaVotaram = sessaoRepository.getById(sessaoOptional
                    .get().getId())
                    .getFormulario()
                    .getIdAssociadosQueVotaram();
            Boolean associadoJaVotou = idDeAssociadosQueJaVotaram.stream()
                    .anyMatch(id -> id.equals(entity.getId()));

//            if (associadoJaVotou) {
//                logger.info("Nao é possivel votar. Associado ja votou anteriormente.");
//                return ResponseEntity.notFound().build();
//            }

            SessaoEntity sessaoEntity = sessaoOptional.get();

            sessaoEntity.getFormulario().getVotos().add(model.getVoto());
            sessaoEntity.getFormulario().getIdAssociadosQueVotaram().add(entity.getId());
            SessaoEntity saved = sessaoRepository.save(sessaoEntity);
            if (saved != null) {
                logger.info("Voto computado com sucesso! ID: {}", SessaoMapper.unmarshall(sessaoEntity).getId());
                return ResponseEntity.ok().header("Content-Type", "application/json")
                        .body(SessaoMapper.unmarshall(sessaoEntity));
            }
            logger.info("Voto nao foi computado!");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao computadar voto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity listaTotalVotos(Long idSessao) {
        try {
            Optional<SessaoEntity> sessaoOptional = sessaoRepository.findById(idSessao);

            if (sessaoOptional.isEmpty()) {
                logger.info("Erro, sessao vazia!");
                return ResponseEntity.notFound().build();
            }
            TotalVotos listTotalVotos = new TotalVotos();
            listTotalVotos.setVotos(sessaoOptional.get().getFormulario().getVotos());
            String total = contarVotos(listTotalVotos.getVotos());
            if (total != null) {
                logger.info("Sucesso na contagem de votos!");
                return ResponseEntity.ok().header("Content-Type", "application/json")
                        .body(total);
            }
            logger.error("Erro ao contabilizar todos os votos!");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao contabilizar todos os votos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    private Boolean validaCPF(String cpfFormatado) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode validaCpfNode = mapper.readTree(client.getValidaCPF(cpfFormatado));

        ValidaCPF validaCPF = new ValidaCPF();
        validaCPF.setStatus(validaCpfNode.get("status").textValue());
        return validaCPF.getStatus().equals(CPF_VALIDO);
    }
    private String contarVotos(List<Voto> votos) {
        Integer simCount = 0;
        Integer naoCount = 0;
        for (Voto voto : votos) {
            if (voto == Voto.SIM) {
                simCount++;
            } else if (voto == Voto.NAO) {
                naoCount++;
            }
        }
        return "Total de votos foi " + simCount + " para sim e " + naoCount + " para nao";
    }
}