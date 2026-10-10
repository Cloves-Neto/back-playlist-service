package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.domain.ListDomain;
import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.core.ports.out.ListDatabasePort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListFinderByName {

    private final ListDatabasePort listDatabasePort;

    public ListFinderByName(ListDatabasePort listDatabasePort) {
        this.listDatabasePort = listDatabasePort;
    }

    @Transactional(readOnly = true)
    public ListResponseDTO execute(String name) {
        // Garantindo que jogue exatamente um 404 Not Found se não achar
        ListDomain list = listDatabasePort.findByNome(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada!"));

        List<MusicResponseDTO> musicResponse = list.getMusics().stream()
                .map(m -> new MusicResponseDTO(
                        m.getId(),
                        m.getTitulo(),
                        m.getArtista(),
                        m.getAlbum(),
                        m.getAno(),
                        m.getGenero()))
                .collect(Collectors.toList());

        return new ListResponseDTO(
                list.getId(),
                list.getNome(),
                list.getDescricao(),
                musicResponse
        );
    }
}
