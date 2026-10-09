package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.dto.Music.MusicRemovalRequestDTO;
import com.playlist_service.api.infrastructure.entity.ListModel;
import com.playlist_service.api.infrastructure.repositories.ListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ListMusicRemover {
    private final ListRepository listRepository;

    public ListMusicRemover(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    @Transactional
    public void execute(String listName, MusicRemovalRequestDTO request) {
        ListModel list = listRepository.findByNome(listName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada!"));

        list.getMusics().removeIf(music -> request.musicIds().contains(music.getId()));

        listRepository.save(list);
    }
}
