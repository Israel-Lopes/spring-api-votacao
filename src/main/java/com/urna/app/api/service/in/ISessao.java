package com.urna.app.api.service.in;

import com.urna.app.api.service.dto.Sessao;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ISessao {
    public ResponseEntity getSessao(HttpServletRequest request, Long id);
    public ResponseEntity<List<Sessao>> getSessaoList(HttpServletRequest request);
    public ResponseEntity createSessao(Sessao model);
    public ResponseEntity patchAtivaSessao(Sessao model);
}