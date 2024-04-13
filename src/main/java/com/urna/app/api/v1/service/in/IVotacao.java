package com.urna.app.api.v1.service.in;

import com.urna.app.api.v1.web.dto.VotoAssociado;
import org.springframework.http.ResponseEntity;

public interface IVotacao {
    public ResponseEntity createVoto(VotoAssociado model);
    public ResponseEntity listaTotalVotos(Long idSessao);
}