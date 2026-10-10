package com.playlist_service.api.infrastructure.adapters.out.persistence;

import com.playlist_service.api.core.domain.ListDomain;
import com.playlist_service.api.core.ports.out.ListDatabasePort;
import com.playlist_service.api.infrastructure.adapters.out.persistence.mapper.ListMapper;
import com.playlist_service.api.infrastructure.entity.ListModel;
import com.playlist_service.api.infrastructure.repositories.ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListPersistenceAdapter implements ListDatabasePort {

    private final ListRepository listRepository;
    private final ListMapper listMapper;

    @Override
    public ListDomain save(ListDomain list) {
        ListModel entity = listMapper.toEntity(list);
        ListModel savedEntity = listRepository.save(entity);
        return listMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<ListDomain> findById(UUID id) {
        return listRepository.findById(id).map(listMapper::toDomain);
    }

    @Override
    public Optional<ListDomain> findByNome(String nome) {
        return listRepository.findByNome(nome).map(listMapper::toDomain);
    }

    @Override
    public List<ListDomain> findAll() {
        return listRepository.findAll().stream()
                .map(listMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(ListDomain list) {
        listRepository.delete(listMapper.toEntity(list));
    }
}
