package com.urna.app.api.web.controller;

import com.urna.app.api.service.VotacaoImpl;
import com.urna.app.api.service.model.VotoAssociado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votacao")
public class VotacaoController {
    @Autowired(required=true)
    private VotacaoImpl service;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity createVotacao(@RequestBody VotoAssociado model) throws Exception {
        return service.createVoto(model);
    }
//    @GetMapping("total/{id}")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public ResponseEntity listaTotalVotos(@RequestBody Long idSessao) throws Exception {
//        return service.listaTotalVotos(idSessao);
//    }
}