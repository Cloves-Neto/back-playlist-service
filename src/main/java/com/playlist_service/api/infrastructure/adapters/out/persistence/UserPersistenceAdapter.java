package com.playlist_service.api.infrastructure.adapters.out.persistence;

import com.playlist_service.api.core.domain.UserDomain;
import com.playlist_service.api.core.ports.out.UserDatabasePort;
import com.playlist_service.api.infrastructure.adapters.out.persistence.mapper.UserMapper;
import com.playlist_service.api.infrastructure.entity.UserModel;
import com.playlist_service.api.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserDatabasePort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDomain save(UserDomain user) {
        UserModel entity = userMapper.toEntity(user);
        UserModel savedEntity = userRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<UserDomain> findByLogin(String login) {
        return userRepository.findUserModelByLogin(login).map(userMapper::toDomain);
    }

    @Override
    public boolean existsByLogin(String login) {
        return userRepository.findUserModelByLogin(login).isPresent();
    }
}
