package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.infrastructure.repositories.MusicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class MusicDeleteById {

    private final MusicRepository musicRepository;

    public MusicDeleteById(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public void execute(UUID id) {
        if (!musicRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada!");
        }
        musicRepository.deleteById(id);
    }
}
