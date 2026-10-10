package com.playlist_service.api.infrastructure.adapters.out.persistence.mapper;

import com.playlist_service.api.core.domain.MusicDomain;
import com.playlist_service.api.infrastructure.entity.MusicModel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MusicMapper {


    public MusicDomain toDomain(MusicModel entity) {
        if (entity == null) return null;
        return MusicDomain.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .artista(entity.getArtista())
                .genero(entity.getGenero())
                .ano(entity.getAno())
                .album(entity.getAlbum())
                .build();
    }

    
    public MusicModel toEntity(MusicDomain domain) {
        if (domain == null) return null;
        return MusicModel.builder()
                .id(domain.getId())
                .titulo(domain.getTitulo())
                .artista(domain.getArtista())
                .genero(domain.getGenero())
                .ano(domain.getAno())
                .album(domain.getAlbum())
                .build();
    }
}
