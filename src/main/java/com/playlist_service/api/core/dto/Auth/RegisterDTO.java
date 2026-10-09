package com.playlist_service.api.core.dto.Auth;

import com.playlist_service.api.infrastructure.entity.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {}
