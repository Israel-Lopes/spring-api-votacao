package com.urna.app.api.service.in;

import com.urna.app.api.service.dto.VotoAssociado;
import org.springframework.http.ResponseEntity;

public interface IVotacao {
    public ResponseEntity createVoto(VotoAssociado model);
    public ResponseEntity listaTotalVotos(Long idSessao);
}