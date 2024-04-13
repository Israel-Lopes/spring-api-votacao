package com.urna.app.api.v1.service.in;

import com.urna.app.api.v1.web.dto.Pauta;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPauta {
    public ResponseEntity getPauta(HttpServletRequest request, Long id);
    public ResponseEntity<List<Pauta>> getPautaList(HttpServletRequest request);
    public ResponseEntity createPauta(Pauta model);
    public ResponseEntity updatePauta(Pauta model);
    public ResponseEntity deletePauta(Long id);
}