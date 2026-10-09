package com.playlist_service.api.core.dto.Music;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record MusicAdditionRequestDTO(
        @NotEmpty(message = "Informe ao menos um ID de música para adicionar")
        List<UUID> musicIds
) {}
