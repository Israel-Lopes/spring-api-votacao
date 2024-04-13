package com.urna.app.api.v1.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.urna.app.api.v1.utils.Voto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class FormularioDeVoto {
    private Long id;
    private List<Voto> votos;
    private List<Long> idAssociadosQueVotaram;
}