package com.urna.app.api.v1.web.mapper;

import com.urna.app.api.v1.persistence.entity.PautaEntity;
import com.urna.app.api.v1.web.dto.Pauta;

import java.util.Collection;
import java.util.stream.Collectors;

public class PautaMapper {
    private PautaMapper() { super(); }
    public static Collection<PautaEntity> marshall(Collection<Pauta> models){
        return models.stream().map(PautaMapper::marshall).collect(Collectors.toList());
    }
    public static Collection<Pauta> unmarshall(Collection<PautaEntity> entitys){
        return entitys.stream().map(PautaMapper::unmarshall).collect(Collectors.toList());
    }
    public static PautaEntity marshall(Pauta model) {
        return PautaEntity.builder()
                .id(model.getId())
                .titulo(model.getTitulo())
                .descricao(model.getDescricao())
                .build();
    }
    public static Pauta unmarshall(PautaEntity entity) {
        return Pauta.builder()
                .Id(entity.getId())
                .titulo(entity.getTitulo())
                .descricao(entity.getDescricao())
                .build();
    }
}