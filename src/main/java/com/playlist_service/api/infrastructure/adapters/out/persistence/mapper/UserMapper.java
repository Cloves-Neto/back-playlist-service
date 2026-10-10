package com.playlist_service.api.infrastructure.adapters.out.persistence.mapper;

import com.playlist_service.api.core.domain.UserDomain;
import com.playlist_service.api.infrastructure.entity.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDomain toDomain(UserModel entity) {
        if (entity == null) return null;
        return UserDomain.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .role(entity.getRole() != null ? com.playlist_service.api.core.domain.UserRole.valueOf(entity.getRole().name()) : null)
                .build();
    }

    public UserModel toEntity(UserDomain domain) {
        if (domain == null) return null;
        return UserModel.builder()
                .id(domain.getId())
                .login(domain.getLogin())
                .password(domain.getPassword())
                .role(domain.getRole() != null ? com.playlist_service.api.infrastructure.entity.UserRole.valueOf(domain.getRole().name()) : null)
                .build();
    }
}
