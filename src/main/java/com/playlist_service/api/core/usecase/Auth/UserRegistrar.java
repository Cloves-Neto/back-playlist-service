package com.playlist_service.api.core.usecase.Auth;

import com.playlist_service.api.core.domain.UserDomain;
import com.playlist_service.api.core.domain.UserRole;
import com.playlist_service.api.core.dto.Auth.RegisterDTO;
import com.playlist_service.api.core.ports.out.PasswordEncoderPort;
import com.playlist_service.api.core.ports.out.UserDatabasePort;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrar {

    private final UserDatabasePort userDatabasePort;
    private final PasswordEncoderPort passwordEncoderPort;

    public UserRegistrar(UserDatabasePort userDatabasePort, PasswordEncoderPort passwordEncoderPort) {
        this.userDatabasePort = userDatabasePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    public void execute(RegisterDTO data) {
        if (userDatabasePort.existsByLogin(data.login())) {
            throw new IllegalArgumentException("User already exists");
        }

        String encryptedPassword = passwordEncoderPort.encode(data.password());
        
        UserDomain newUser = UserDomain.builder()
                .login(data.login())
                .password(encryptedPassword)
                .role(data.role() != null ? UserRole.valueOf(data.role().name()) : UserRole.USER)
                .build();

        userDatabasePort.save(newUser);
    }
}
