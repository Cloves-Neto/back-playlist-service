package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.core.ports.out.MusicDatabasePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicFinderByName {

    private final MusicDatabasePort musicDatabasePort;

    public MusicFinderByName(MusicDatabasePort musicDatabasePort) {
        this.musicDatabasePort = musicDatabasePort;
    }

    public List<MusicResponseDTO> execute(String nome) {
        return musicDatabasePort.findByTituloContainingIgnoreCase(nome)
                .stream()
                .map(music -> new MusicResponseDTO(
                        music.getId(),
                        music.getTitulo(),
                        music.getArtista(),
                        music.getGenero(),
                        music.getAno(),
                        music.getAlbum()
                ))
                .collect(Collectors.toList());
    }
}
