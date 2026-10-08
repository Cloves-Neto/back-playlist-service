package com.playlist_service.api.core.dto.Musica;

import java.util.UUID;

public record MusicaResponseDTO(
        UUID id,
        String titulo,
        String artista,
        String genero,
        Integer ano,
        String album
) {
}
