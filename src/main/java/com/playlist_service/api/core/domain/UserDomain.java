package com.playlist_service.api.core.domain;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class UserDomain {
    private UUID id;
    private String login;
    private String password;
    private UserRole role;
}
