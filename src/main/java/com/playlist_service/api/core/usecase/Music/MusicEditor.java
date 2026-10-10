package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.core.domain.MusicDomain;
import com.playlist_service.api.core.dto.Music.MusicRequestDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.core.ports.out.MusicDatabasePort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class MusicEditor {

    private final MusicDatabasePort musicDatabasePort;

    public MusicEditor(MusicDatabasePort musicDatabasePort) {
        this.musicDatabasePort = musicDatabasePort;
    }

    public MusicResponseDTO execute(UUID id, MusicRequestDTO request) {
        MusicDomain music = musicDatabasePort.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada!"));

        music.setTitulo(request.titulo());
        music.setArtista(request.artista());
        music.setGenero(request.genero());
        music.setAno(request.ano());
        music.setAlbum(request.album());

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
