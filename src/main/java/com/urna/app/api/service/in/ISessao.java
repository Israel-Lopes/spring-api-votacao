package com.urna.app.api.service.in;

import com.urna.app.api.service.model.Sessao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ISessao {
    public ResponseEntity getSessao(HttpServletRequest request, Long id);
    public ResponseEntity<List<Sessao>> getSessaoList(HttpServletRequest request);
    public ResponseEntity createSessao(@RequestBody Sessao model);
    public ResponseEntity patchAtivaSessao(Long id, @RequestBody Sessao model);
}
