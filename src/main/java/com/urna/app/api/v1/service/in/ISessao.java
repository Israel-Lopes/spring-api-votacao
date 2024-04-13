package com.urna.app.api.v1.service.in;

import com.urna.app.api.v1.web.dto.Sessao;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ISessao {
    public ResponseEntity getSessao(HttpServletRequest request, Long id);
    public ResponseEntity<List<Sessao>> getSessaoList(HttpServletRequest request);
    public ResponseEntity createSessao(Sessao model);
    public ResponseEntity patchAtivaSessao(Sessao model);
}