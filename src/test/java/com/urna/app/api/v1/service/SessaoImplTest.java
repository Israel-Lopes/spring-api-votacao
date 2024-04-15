package com.urna.app.api.v1.service;

import com.urna.app.api.v1.persistence.entity.FormularioDeVotoEntity;
import com.urna.app.api.v1.persistence.entity.SessaoEntity;
import com.urna.app.api.v1.repository.SessaoRepository;
import com.urna.app.api.v1.web.dto.Sessao;
import com.urna.app.api.v1.web.mapper.SessaoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessaoImplTest {
    @Mock
    private SessaoRepository repository;
    @InjectMocks
    private SessaoImpl sessaoService;
    @Test
    public void testGetSessao() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Long id = 1L;
        SessaoEntity sessaoEntity = new SessaoEntity();
        sessaoEntity.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(sessaoEntity));

        ResponseEntity responseEntity = sessaoService.getSessao(request, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sessaoEntity.getId(), ((Sessao) responseEntity.getBody()).getId());
    }
    @Test
    public void testGetSessao_NotFound() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = sessaoService.getSessao(request, id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    public void testGetSessaoList() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        List<SessaoEntity> sessaoEntities = new ArrayList<>();
        SessaoEntity sessaoEntity1 = new SessaoEntity();
        sessaoEntity1.setId(1L);
        sessaoEntities.add(sessaoEntity1);
        SessaoEntity sessaoEntity2 = new SessaoEntity();
        sessaoEntity2.setId(2L);
        sessaoEntities.add(sessaoEntity2);
        when(repository.findAll()).thenReturn(sessaoEntities);

        ResponseEntity responseEntity = sessaoService.getSessaoList(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, ((List<Sessao>) responseEntity.getBody()).size());
    }
//    @Test
//    public void testCreateSessao() {
//        Sessao sessao = new Sessao();
//        sessao.setFormulario(new FormularioDeVotoEntity());
//        SessaoEntity sessaoEntity = new SessaoEntity();
//        sessaoEntity.setId(1L);
//        sessao.getFormulario().setIdAssociadosQueVotaram(new ArrayList<>());
//        sessao.getFormulario().setVotos(new ArrayList<>());
//        when(repository.save(any(SessaoEntity.class))).thenReturn(sessaoEntity);
//
//        ResponseEntity responseEntity = sessaoService.createSessao(sessao);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(sessaoEntity.getId(), ((Sessao) responseEntity.getBody()).getId());
//    }
//
//    @Test
//    public void testPatchAtivaSessao() {
//        Sessao sessao = new Sessao();
//        sessao.setId(1L);
//        SessaoEntity sessaoEntity = new SessaoEntity();
//        sessaoEntity.setId(1L);
//        sessaoEntity.setVotacaoEmAndamento(false);
//        when(repository.findById(sessao.getId())).thenReturn(Optional.of(sessaoEntity));
//        when(repository.save(any(SessaoEntity.class))).thenReturn(sessaoEntity);
//
//        ResponseEntity responseEntity = sessaoService.patchAtivaSessao(sessao);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(true, ((Sessao) responseEntity.getBody()).getVotacaoEmAndamento());
//    }
}
