package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.infrastructure.repositories.MusicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicFinderByName {

    private final MusicRepository musicRepository;

    public MusicFinderByName(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public List<MusicResponseDTO> execute(String nome) {
        return musicRepository.findByTituloContainingIgnoreCase(nome)
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
