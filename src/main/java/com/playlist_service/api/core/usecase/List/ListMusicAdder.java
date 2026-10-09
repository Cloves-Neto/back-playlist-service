package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.core.dto.Music.MusicAdditionRequestDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.infrastructure.entity.ListModel;
import com.playlist_service.api.infrastructure.entity.MusicModel;
import com.playlist_service.api.infrastructure.repositories.ListRepository;
import com.playlist_service.api.infrastructure.repositories.MusicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListMusicAdder {

    private final ListRepository listRepository;
    private final MusicRepository musicRepository;

    public ListMusicAdder(ListRepository listRepository, MusicRepository musicRepository) {
        this.listRepository = listRepository;
        this.musicRepository = musicRepository;
    }

    @Transactional
    public ListResponseDTO execute(String listName, MusicAdditionRequestDTO request) {
        ListModel list = listRepository.findByNome(listName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada!"));

        List<MusicModel> musicsToAdd = musicRepository.findAllById(request.musicIds());
        list.getMusics().addAll(musicsToAdd);

        ListModel saved = listRepository.save(list);

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
