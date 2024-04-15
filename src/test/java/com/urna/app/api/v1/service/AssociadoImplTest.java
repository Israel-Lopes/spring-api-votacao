package com.urna.app.api.v1.service;

import com.urna.app.api.v1.persistence.entity.AssociadoEntity;
import com.urna.app.api.v1.repository.AssociadoRepository;
import com.urna.app.api.v1.utils.FormatarCpf;
import com.urna.app.api.v1.web.dto.Associado;
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
public class AssociadoImplTest {
    @Mock
    private AssociadoRepository repository;
    @Mock
    private FormatarCpf formatarCpf;
    @InjectMocks
    private AssociadoImpl associadoService;
    @Test
    public void testGetAssociado() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Long id = 1L;
        AssociadoEntity associadoEntity = new AssociadoEntity();
        associadoEntity.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(associadoEntity));

        ResponseEntity responseEntity = associadoService.getAssociado(request, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(associadoEntity.getId(), ((Associado) responseEntity.getBody()).getId());
    }
    @Test
    public void testGetAssociado_NotFound() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = associadoService.getAssociado(request, id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    public void testGetAssociadoList() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        List<AssociadoEntity> associadoEntities = new ArrayList<>();
        AssociadoEntity associadoEntity1 = new AssociadoEntity();
        associadoEntity1.setId(1L);
        associadoEntities.add(associadoEntity1);
        AssociadoEntity associadoEntity2 = new AssociadoEntity();
        associadoEntity2.setId(2L);
        associadoEntities.add(associadoEntity2);
        when(repository.findAll()).thenReturn(associadoEntities);

        ResponseEntity responseEntity = associadoService.getAssociadoList(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, ((List<Associado>) responseEntity.getBody()).size());
    }
    @Test
    public void testCreateAssociado() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Associado associado = new Associado();
        associado.setCpf("12345678900");
        AssociadoEntity associadoEntity = new AssociadoEntity();
        associadoEntity.setId(1L);
        when(formatarCpf.replace(anyString())).thenReturn("123.456.789-00");
        when(repository.save(any(AssociadoEntity.class))).thenReturn(associadoEntity);

        ResponseEntity responseEntity = associadoService.createAssociado(associado);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(associadoEntity.getId(), ((Associado) responseEntity.getBody()).getId());
    }
}