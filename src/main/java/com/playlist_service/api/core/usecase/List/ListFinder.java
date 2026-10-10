package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.core.ports.out.ListDatabasePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListFinder {

    private final ListDatabasePort listDatabasePort;

    public ListFinder(ListDatabasePort listDatabasePort) { 
        this.listDatabasePort = listDatabasePort; 
    }

    @Transactional(readOnly = true)
    public List<ListResponseDTO> execute() {
        return listDatabasePort.findAll().stream().map(list -> {
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
        }).collect(Collectors.toList());
    }
}
