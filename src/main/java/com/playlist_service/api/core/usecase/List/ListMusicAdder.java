package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.domain.ListDomain;
import com.playlist_service.api.core.domain.MusicDomain;
import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.core.dto.Music.MusicAdditionRequestDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.core.ports.out.ListDatabasePort;
import com.playlist_service.api.core.ports.out.MusicDatabasePort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListMusicAdder {

    private final ListDatabasePort listDatabasePort;
    private final MusicDatabasePort musicDatabasePort;

    public ListMusicAdder(ListDatabasePort listDatabasePort, MusicDatabasePort musicDatabasePort) {
        this.listDatabasePort = listDatabasePort;
        this.musicDatabasePort = musicDatabasePort;
    }

    @Transactional
    public ListResponseDTO execute(String listName, MusicAdditionRequestDTO request) {
        ListDomain list = listDatabasePort.findByNome(listName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada!"));

        List<MusicDomain> musicsToAdd = musicDatabasePort.findAllById(request.musicIds());

        if (musicsToAdd.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma das músicas informadas foi encontrada!");
        }

        list.getMusics().addAll(musicsToAdd);

        ListDomain saved = listDatabasePort.save(list);

        List<MusicResponseDTO> musicResponse = saved.getMusics().stream()
                .map(m -> new MusicResponseDTO(
                        m.getId(),
                        m.getTitulo(),
                        m.getArtista(),
                        m.getAlbum(),
                        m.getAno(),
                        m.getGenero()))
                .collect(Collectors.toList());

        return new ListResponseDTO(
                saved.getId(),
                saved.getNome(),
                saved.getDescricao(),
                musicResponse
        );
    }
}
