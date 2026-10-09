package com.playlist_service.api.core.dto.List;

import java.util.List;

import com.playlist_service.api.core.dto.Music.MusicRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record ListRequestDTO(
        @NotBlank(message = "O nome é obrigatório") String nome,
        String descricao,
        @Valid List<MusicRequestDTO> musicas
) {

}
