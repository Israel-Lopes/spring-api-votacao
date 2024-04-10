package com.urna.app.api.web.controller;

import com.urna.app.api.service.AssociadoImpl;
import com.urna.app.api.web.dto.Associado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/associado")
//@RequiredArgsConstructor
public class AssociadoController {
    @Autowired(required=true)
    private AssociadoImpl service;
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity getAssociado(HttpServletRequest request, @PathVariable Long id) throws Exception {
        return service.getAssociado(request, id);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Associado>> getAssociadoList(HttpServletRequest request) throws Exception {
        return service.getAssociadoList(request);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity createAssociado(@RequestBody Associado model) throws Exception {
        return service.createAssociado(model);
    }
}