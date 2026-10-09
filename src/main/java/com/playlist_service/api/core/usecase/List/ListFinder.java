package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.infrastructure.repositories.ListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListFinder {

    private final ListRepository listRepository;

    public ListFinder(ListRepository listRepository) { 
        this.listRepository = listRepository; 
    }

    @Transactional(readOnly = true)
    public List<ListResponseDTO> execute() {
        return listRepository.findAll().stream().map(list -> {
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
