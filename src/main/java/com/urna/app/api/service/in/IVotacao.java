package com.urna.app.api.service.in;

import com.urna.app.api.service.model.IdSessao;
import com.urna.app.api.service.model.VotoAssociado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IVotacao {
    public ResponseEntity createVoto(@RequestBody VotoAssociado model);
    public ResponseEntity listaTotalVotos(@PathVariable Long idSessao);
}