package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.core.ports.out.MusicDatabasePort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class MusicDeleteById {

    private final MusicDatabasePort musicDatabasePort;

    public MusicDeleteById(MusicDatabasePort musicDatabasePort) {
        this.musicDatabasePort = musicDatabasePort;
    }

    public void execute(UUID id) {
        if (!musicDatabasePort.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada!");
        }
        musicDatabasePort.deleteById(id);
    }
}
