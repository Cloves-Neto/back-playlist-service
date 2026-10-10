package com.playlist_service.api.infrastructure.adapters.out.persistence.mapper;

import com.playlist_service.api.core.domain.ListDomain;
import com.playlist_service.api.infrastructure.entity.ListModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListMapper {

    private final MusicMapper musicMapper;

    public ListDomain toDomain(ListModel entity) {
        if (entity == null) return null;
        return ListDomain.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .musics(entity.getMusics() == null ? null : entity.getMusics().stream()
                        .map(musicMapper::toDomain)
                        .collect(Collectors.toSet()))
                .build();
    }

    public ListModel toEntity(ListDomain domain) {
        if (domain == null) return null;
        return ListModel.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .descricao(domain.getDescricao())
                .musics(domain.getMusics() == null ? null : domain.getMusics().stream()
                        .map(musicMapper::toEntity)
                        .collect(Collectors.toSet()))
                .build();
    }
}
