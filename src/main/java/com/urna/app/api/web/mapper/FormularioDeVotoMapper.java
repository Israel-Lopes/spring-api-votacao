package com.urna.app.api.web.mapper;

import com.urna.app.api.persistence.entity.FormularioDeVotoEntity;
import com.urna.app.api.service.model.FormularioDeVoto;

import java.util.Collection;
import java.util.stream.Collectors;

public class FormularioDeVotoMapper {
    private FormularioDeVotoMapper() { super(); }
    public static Collection<FormularioDeVotoEntity> marshall(Collection<FormularioDeVoto> models){
        return models.stream().map(FormularioDeVotoMapper::marshall).collect(Collectors.toList());
    }
    public static Collection<FormularioDeVoto> unmarshall(Collection<FormularioDeVotoEntity> entitys){
        return entitys.stream().map(FormularioDeVotoMapper::unmarshall).collect(Collectors.toList());
    }
    public static FormularioDeVotoEntity marshall(FormularioDeVoto model) {
        return FormularioDeVotoEntity.builder()
                .id(model.getId())
                .votos(model.getVotos())
                .idAssociadosQueVotaram(model.getIdAssociadosQueVotaram())
                .build();
    }
    public static FormularioDeVoto unmarshall(FormularioDeVotoEntity entity) {
        return FormularioDeVoto.builder()
                .id(entity.getId())
                .votos(entity.getVotos())
                .idAssociadosQueVotaram(entity.getIdAssociadosQueVotaram())
                .build();
    }
}