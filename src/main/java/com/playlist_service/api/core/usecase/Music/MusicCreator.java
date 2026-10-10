package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.core.domain.MusicDomain;
import com.playlist_service.api.core.dto.Music.MusicRequestDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.core.ports.out.MusicDatabasePort;
import org.springframework.stereotype.Service;

@Service
public class MusicCreator {

    private final MusicDatabasePort musicDatabasePort;

    public MusicCreator(MusicDatabasePort musicDatabasePort) {
        this.musicDatabasePort = musicDatabasePort;
    }

    public MusicResponseDTO execute(MusicRequestDTO request) {
        MusicDomain music = MusicDomain.builder()
                .titulo(request.titulo())
                .artista(request.artista())
                .genero(request.genero())
                .ano(request.ano())
                .album(request.album())
                .build();

        MusicDomain saved = musicDatabasePort.save(music);

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
