package com.playlist_service.api.core.ports.out;

import com.playlist_service.api.core.domain.UserDomain;

import java.util.Optional;

public interface UserDatabasePort {
    UserDomain save(UserDomain user);
    Optional<UserDomain> findByLogin(String login);
    boolean existsByLogin(String login);
}
