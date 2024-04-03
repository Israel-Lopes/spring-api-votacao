package com.urna.app.api.web.mapper;

import com.urna.app.api.persistence.entity.AssociadoEntity;
import com.urna.app.api.service.model.Associado;

import java.util.Collection;
import java.util.stream.Collectors;

public class AssociadoMapper {
    private AssociadoMapper() { super(); }
    public static Collection<AssociadoEntity> marshall(Collection<Associado> models){
        return models.stream().map(AssociadoMapper::marshall).collect(Collectors.toList());
    }
    public static Collection<Associado> unmarshall(Collection<AssociadoEntity> entitys){
        return entitys.stream().map(AssociadoMapper::unmarshall).collect(Collectors.toList());
    }
    public static AssociadoEntity marshall(Associado model) {
        return AssociadoEntity.builder()
                .id(model.getId())
                .cpf(model.getCpf())
                .build();
    }
    public static Associado unmarshall(AssociadoEntity entity) {
        return Associado.builder()
                .Id(entity.getId())
                .cpf(entity.getCpf())
                .build();
    }
}
