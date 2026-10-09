package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.core.dto.Music.MusicRequestDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.infrastructure.entity.MusicModel;
import com.playlist_service.api.infrastructure.repositories.MusicRepository;
import org.springframework.stereotype.Service;

@Service
public class MusicCreator {

    private final MusicRepository musicRepository;

    public MusicCreator(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public MusicResponseDTO execute(MusicRequestDTO request) {
        MusicModel musicModel = MusicModel.builder()
                .titulo(request.titulo())
                .artista(request.artista())
                .genero(request.genero())
                .ano(request.ano())
                .album(request.album())
                .build();

        MusicModel saved = musicRepository.save(musicModel);

        return new MusicResponseDTO(
                saved.getId(),
                saved.getTitulo(),
                saved.getArtista(),
                saved.getGenero(),
                saved.getAno(),
                saved.getAlbum()
        );
    }
}
