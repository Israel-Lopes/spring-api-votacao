package com.urna.app.api.v1.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessao")
public class SessaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tempo_da_votacao", columnDefinition = "TIME DEFAULT '00:01:00'", nullable = false)
    private LocalTime tempoDaVotacao;

    @Column(name = "votacao_em_andamento", columnDefinition = "BIT DEFAULT 0")
    private Boolean votacaoEmAndamento;

    @Column(name = "inicio")
    private LocalDateTime inicioDaContagem;

    @Column(name = "fim")
    private LocalDateTime fimDaContagem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "formulario_id")
    private FormularioDeVotoEntity formulario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pauta_id")
    private PautaEntity pauta;
}