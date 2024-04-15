package com.urna.app.api.v1.service;

import com.urna.app.api.v1.persistence.entity.AssociadoEntity;
import com.urna.app.api.v1.persistence.entity.SessaoEntity;
import com.urna.app.api.v1.repository.AssociadoRepository;
import com.urna.app.api.v1.repository.SessaoRepository;
import com.urna.app.api.v1.utils.FormatarCpf;
import com.urna.app.api.v1.utils.Voto;
import com.urna.app.api.v1.web.dto.VotoAssociado;
import com.urna.app.client.ValidaCPFClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VotacaoImplTest {
    @Mock
    private AssociadoRepository associadoRepository;
    @Mock
    private SessaoRepository sessaoRepository;
    @Mock
    private ValidaCPFClient validaCPFClient;
    @Mock
    private FormatarCpf formatarCpf;
    @InjectMocks
    private VotacaoImpl votacaoService;
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
//    @Test
//    public void testCreateVoto_Success() {
//        VotoAssociado votoAssociado = new VotoAssociado();
//        votoAssociado.setCpf("12345678900");
//        votoAssociado.setIdSessao(1L);
//        votoAssociado.setVoto(Voto.SIM);
//
//        AssociadoEntity associadoEntity = new AssociadoEntity();
//        associadoEntity.setId(1L);
//
//        SessaoEntity sessaoEntity = new SessaoEntity();
//        sessaoEntity.setId(1L);
//
//        when(associadoRepository.findByCpf(any())).thenReturn(associadoEntity);
//        when(sessaoRepository.findById(anyLong())).thenReturn(Optional.of(sessaoEntity));
//        when(sessaoRepository.save(any())).thenReturn(sessaoEntity);
//
//        ResponseEntity response = votacaoService.createVoto(votoAssociado);
//
//        assertEquals(200, response.getStatusCodeValue());
//    }
    @Test
    public void testCreateVoto_AssociadoNotFound() {
        VotoAssociado votoAssociado = new VotoAssociado();
        votoAssociado.setCpf("12345678900");
        votoAssociado.setIdSessao(1L);
        votoAssociado.setVoto(Voto.SIM);

        when(associadoRepository.findByCpf(any())).thenReturn(null);

        ResponseEntity response = votacaoService.createVoto(votoAssociado);

        assertEquals(404, response.getStatusCodeValue());
    }
}