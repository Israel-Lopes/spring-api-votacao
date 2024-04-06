package com.urna.app.api.web.mapper;

import com.urna.app.api.persistence.entity.SessaoEntity;
import com.urna.app.api.service.dto.Sessao;

import java.util.Collection;
import java.util.stream.Collectors;

public class SessaoMapper {
    private SessaoMapper() { super(); }
    public static Collection<SessaoEntity> marshall(Collection<Sessao> models){
        return models.stream().map(SessaoMapper::marshall).collect(Collectors.toList());
    }
    public static Collection<Sessao> unmarshall(Collection<SessaoEntity> entitys){
        return entitys.stream().map(SessaoMapper::unmarshall).collect(Collectors.toList());
    }
    public static SessaoEntity marshall(Sessao model) {
        return SessaoEntity.builder()
                .id(model.getId())
                .tempoDaVotacao(model.getTempoDaVotacao())
                .votacaoEmAndamento(model.getVotacaoEmAndamento())
                .inicioDaContagem(model.getInicioDaContagem())
                .fimDaContagem(model.getFimDaContagem())
                .formulario(model.getFormulario())
                .pauta(model.getPauta())
                .build();
    }
    public static Sessao unmarshall(SessaoEntity entity) {
        return Sessao.builder()
                .id(entity.getId())
                .tempoDaVotacao(entity.getTempoDaVotacao())
                .votacaoEmAndamento(entity.getVotacaoEmAndamento())
                .inicioDaContagem(entity.getInicioDaContagem())
                .fimDaContagem(entity.getFimDaContagem())
                .formulario(entity.getFormulario())
                .pauta(entity.getPauta())
                .build();
    }
}