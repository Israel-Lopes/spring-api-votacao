package com.urna.app.api.v1.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.urna.app.api.v1.utils.Voto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class VotoAssociado {
    private String cpf;
    private Voto voto;
    private Long idSessao;
}