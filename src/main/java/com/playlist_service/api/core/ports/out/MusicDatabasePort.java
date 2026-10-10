package com.playlist_service.api.core.ports.out;

import com.playlist_service.api.core.domain.MusicDomain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MusicDatabasePort {
    MusicDomain save(MusicDomain music);
    Optional<MusicDomain> findById(UUID id);
    List<MusicDomain> findByTituloContainingIgnoreCase(String titulo);
    List<MusicDomain> findAllById(Iterable<UUID> ids);
    List<MusicDomain> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
    void delete(MusicDomain music);
}
