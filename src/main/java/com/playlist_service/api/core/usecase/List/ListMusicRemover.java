package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.domain.ListDomain;
import com.playlist_service.api.core.dto.Music.MusicRemovalRequestDTO;
import com.playlist_service.api.core.ports.out.ListDatabasePort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ListMusicRemover {
    private final ListDatabasePort listDatabasePort;

    public ListMusicRemover(ListDatabasePort listDatabasePort) {
        this.listDatabasePort = listDatabasePort;
    }

    @Transactional
    public void execute(String listName, MusicRemovalRequestDTO request) {
        ListDomain list = listDatabasePort.findByNome(listName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada!"));

        list.getMusics().removeIf(music -> request.musicIds().contains(music.getId()));

        listDatabasePort.save(list);
    }
}
