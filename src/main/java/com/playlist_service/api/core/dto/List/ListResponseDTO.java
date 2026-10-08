package com.playlist_service.api.core.dto.List;

import java.util.UUID;
import java.util.List;
import com.playlist_service.api.core.dto.Musica.MusicaResponseDTO;

public record ListResponseDTO(
    UUID id,
    String nome,
    String descricao,
    List<MusicaResponseDTO> musicas
) {
}
