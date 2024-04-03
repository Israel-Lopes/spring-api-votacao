package com.urna.app.api.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.urna.app.api.persistence.entity.FormularioDeVotoEntity;
import com.urna.app.api.persistence.entity.PautaEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Sessao {
    private Long id;
    private LocalTime tempoDaVotacao;
    private Boolean votacaoEmAndamento;
    private LocalDateTime inicioDaContagem;
    private LocalDateTime fimDaContagem;
    private FormularioDeVotoEntity formulario;
    private PautaEntity pauta;
}
