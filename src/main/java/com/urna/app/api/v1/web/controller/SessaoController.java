package com.urna.app.api.v1.web.controller;

import com.urna.app.api.v1.service.SessaoImpl;
import com.urna.app.api.v1.web.dto.Sessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v1/sessao")
public class SessaoController {
    @Autowired(required=true)
    private SessaoImpl service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity getSessao(HttpServletRequest request, @PathVariable Long id) throws Exception {
        return service.getSessao(request, id);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Sessao>> getSessaoList(HttpServletRequest request) throws Exception {
        return service.getSessaoList(request);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity createSessao(@RequestBody Sessao model) throws Exception {
        return service.createSessao(model);
    }
    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity patchSessao(@RequestBody Sessao model) throws Exception {
        return service.patchAtivaSessao(model);
    }
}