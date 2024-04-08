package com.urna.app.api.web.controller;

import com.urna.app.api.service.PautaImpl;
import com.urna.app.api.web.dto.Pauta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/pauta")
public class PautaController {
    @Autowired(required=true)
    private PautaImpl service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity getPauta(HttpServletRequest request, @PathVariable Long id) throws Exception {
        return service.getPauta(request, id);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Pauta>> getPautaList(HttpServletRequest request) throws Exception {
        return service.getPautaList(request);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity createPauta(@RequestBody Pauta model) throws Exception {
        return service.createPauta(model);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity updatePauta(@RequestBody Pauta model) throws Exception {
        return service.updatePauta(model);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity deletePauta(@PathVariable Long id) throws Exception {
        return service.deletePauta(id);
    }
}