package com.playlist_service.api.core.ports.out;

import com.playlist_service.api.core.domain.ListDomain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListDatabasePort {
    ListDomain save(ListDomain list);
    Optional<ListDomain> findById(UUID id);
    Optional<ListDomain> findByNome(String nome);
    List<ListDomain> findAll();
    void delete(ListDomain list);
}
