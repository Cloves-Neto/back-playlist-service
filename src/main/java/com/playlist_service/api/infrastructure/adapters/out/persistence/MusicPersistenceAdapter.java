package com.playlist_service.api.infrastructure.adapters.out.persistence;

import com.playlist_service.api.core.domain.MusicDomain;
import com.playlist_service.api.core.ports.out.MusicDatabasePort;
import com.playlist_service.api.infrastructure.adapters.out.persistence.mapper.MusicMapper;
import com.playlist_service.api.infrastructure.entity.MusicModel;
import com.playlist_service.api.infrastructure.repositories.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicPersistenceAdapter implements MusicDatabasePort {

    private final MusicRepository musicRepository;
    private final MusicMapper musicMapper;

    @Override
    public MusicDomain save(MusicDomain music) {
        MusicModel entity = musicMapper.toEntity(music);
        MusicModel savedEntity = musicRepository.save(entity);
        return musicMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<MusicDomain> findById(UUID id) {
        return musicRepository.findById(id).map(musicMapper::toDomain);
    }

    @Override
    public List<MusicDomain> findByTituloContainingIgnoreCase(String titulo) {
        return musicRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(musicMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicDomain> findAllById(Iterable<UUID> ids) {
        return musicRepository.findAllById(ids).stream()
                .map(musicMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicDomain> findAll() {
        return musicRepository.findAll().stream()
                .map(musicMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(UUID id) {
        return musicRepository.existsById(id);
    }

    @Override
    public void deleteById(UUID id) {
        musicRepository.deleteById(id);
    }

    @Override
    public void delete(MusicDomain music) {
        musicRepository.delete(musicMapper.toEntity(music));
    }
}
