package com.urna.app.api.v1.service.in;

import com.urna.app.api.v1.web.dto.Associado;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAssociado {
    public ResponseEntity getAssociado(HttpServletRequest request, Long id);
    public ResponseEntity<List<Associado>> getAssociadoList(HttpServletRequest request);
    public ResponseEntity createAssociado(Associado model);
}