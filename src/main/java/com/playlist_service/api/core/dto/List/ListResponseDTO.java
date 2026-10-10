package com.playlist_service.api.core.dto.List;

import java.util.UUID;
import java.util.List;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ListResponseDTO(
    UUID id,
    String nome,
    String descricao,
    @JsonProperty("musicas")
    List<MusicResponseDTO> musicas
) {
    @JsonProperty("musics")
    public List<MusicResponseDTO> musics() {
        return musicas;
    }
}