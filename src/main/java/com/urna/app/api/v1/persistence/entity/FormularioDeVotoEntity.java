package com.urna.app.api.v1.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.urna.app.api.v1.utils.Voto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "voto")
public class FormularioDeVotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ElementCollection
    @Column(name = "voto")
    private List<Voto> votos;

    @ElementCollection
    @Column(name = "associado_id")
    private List<Long> idAssociadosQueVotaram;
}