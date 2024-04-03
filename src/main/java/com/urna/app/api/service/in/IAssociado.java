package com.urna.app.api.service.in;

import com.urna.app.api.service.model.Associado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAssociado {
    public ResponseEntity getAssociado(HttpServletRequest request, Long id);
    public ResponseEntity<List<Associado>> getAssociadoList(HttpServletRequest request);
    public ResponseEntity createAssociado(@RequestBody Associado model);
    public ResponseEntity updateAssociado(@RequestBody Associado model);
    public ResponseEntity deleteAssociado(@PathVariable Long id);
}