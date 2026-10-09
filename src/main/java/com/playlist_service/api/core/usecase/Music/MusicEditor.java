package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.core.dto.Music.MusicRequestDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.infrastructure.entity.MusicModel;
import com.playlist_service.api.infrastructure.repositories.MusicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class MusicEditor {

    private final MusicRepository musicRepository;

    public MusicEditor(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public MusicResponseDTO execute(UUID id, MusicRequestDTO request) {
        MusicModel music = musicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada!"));

        music.setTitulo(request.titulo());
        music.setArtista(request.artista());
        music.setGenero(request.genero());
        music.setAno(request.ano());
        music.setAlbum(request.album());

        MusicModel saved = musicRepository.save(music);

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
