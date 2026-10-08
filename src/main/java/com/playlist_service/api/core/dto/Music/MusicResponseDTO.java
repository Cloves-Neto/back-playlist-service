package com.playlist_service.api.core.dto.Music;

import java.util.UUID;

public record MusicResponseDTO(
        UUID id,
        String titulo,
        String artista,
        String genero,
        Integer ano,
        String album
) {
}
