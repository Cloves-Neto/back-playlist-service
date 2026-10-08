package com.playlist_service.api.core.dto.Musica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MusicaRequestDTO(
        @NotBlank(message = "O título é obrigatório") String titulo,
        @NotBlank(message = "O artista é obrigatório") String atista,
        @NotBlank(message = "O genero é obrigatório") String genero,
        @NotNull(message = "O ano é obrigatório") Integer ano,
        @NotBlank(message = "O album é obrigatório") String album
) {}
