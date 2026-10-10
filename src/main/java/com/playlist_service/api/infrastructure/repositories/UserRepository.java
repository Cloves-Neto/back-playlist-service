package com.playlist_service.api.infrastructure.repositories;

import com.playlist_service.api.infrastructure.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserDetails findByLogin(String login);
    java.util.Optional<UserModel> findUserModelByLogin(String login);
}
