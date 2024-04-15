package com.urna.app.api.v1.service;

import com.urna.app.api.v1.persistence.entity.PautaEntity;
import com.urna.app.api.v1.repository.PautaRepository;
import com.urna.app.api.v1.web.dto.Pauta;
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
public class PautaImplTest {
    @Mock
    private PautaRepository repository;
    @InjectMocks
    private PautaImpl pautaService;
    @Test
    public void testGetPauta() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Long id = 1L;
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(pautaEntity));

        ResponseEntity responseEntity = pautaService.getPauta(request, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pautaEntity.getId(), ((Pauta) responseEntity.getBody()).getId());
    }
    @Test
    public void testGetPauta_NotFound() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = pautaService.getPauta(request, id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    public void testGetPautaList() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        List<PautaEntity> pautaEntities = new ArrayList<>();
        PautaEntity pautaEntity1 = new PautaEntity();
        pautaEntity1.setId(1L);
        pautaEntities.add(pautaEntity1);
        PautaEntity pautaEntity2 = new PautaEntity();
        pautaEntity2.setId(2L);
        pautaEntities.add(pautaEntity2);
        when(repository.findAll()).thenReturn(pautaEntities);

        ResponseEntity responseEntity = pautaService.getPautaList(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, ((List<Pauta>) responseEntity.getBody()).size());
    }
    @Test
    public void testCreatePauta() {
        // Arrange
        Pauta pauta = new Pauta();
        pauta.setTitulo("Titulo da Pauta");
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        when(repository.save(any(PautaEntity.class))).thenReturn(pautaEntity);

        ResponseEntity responseEntity = pautaService.createPauta(pauta);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pautaEntity.getId(), ((Pauta) responseEntity.getBody()).getId());
    }
    @Test
    public void testUpdatePauta() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Titulo atualizado");
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        when(repository.findById(pauta.getId())).thenReturn(Optional.of(pautaEntity));
        when(repository.save(any(PautaEntity.class))).thenReturn(pautaEntity);

        ResponseEntity responseEntity = pautaService.updatePauta(pauta);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pauta.getTitulo(), ((Pauta) responseEntity.getBody()).getTitulo());
    }
    @Test
    public void testDeletePauta() {
        Long id = 1L;
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(pautaEntity));

        ResponseEntity responseEntity = pautaService.deletePauta(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(id, responseEntity.getBody());
        verify(repository, times(1)).deleteById(id);
    }
}