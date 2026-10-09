package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.infrastructure.entity.ListModel;
import com.playlist_service.api.infrastructure.repositories.ListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ListDeleteByName {

    private final ListRepository listRepository;

    public ListDeleteByName(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    @Transactional
    public void execute(String name) {
        ListModel list = listRepository.findByNome(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada!"));

        listRepository.delete(list);
    }
}
