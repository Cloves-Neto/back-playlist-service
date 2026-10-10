package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.domain.ListDomain;
import com.playlist_service.api.core.ports.out.ListDatabasePort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ListDeleteByName {

    private final ListDatabasePort listDatabasePort;

    public ListDeleteByName(ListDatabasePort listDatabasePort) {
        this.listDatabasePort = listDatabasePort;
    }

    @Transactional
    public void execute(String name) {
        ListDomain list = listDatabasePort.findByNome(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada!"));

        listDatabasePort.delete(list);
    }
}
