package com.playlist_service.api.infrastructure.web.controller;

import com.playlist_service.api.core.dto.Auth.AuthenticationDTO;
import com.playlist_service.api.core.dto.Auth.LoginResponseDTO;
import com.playlist_service.api.core.dto.Auth.RegisterDTO;
import com.playlist_service.api.infrastructure.entity.UserModel;
import com.playlist_service.api.infrastructure.entity.UserRole;
import com.playlist_service.api.infrastructure.repositories.UserRepository;
import com.playlist_service.api.infrastructure.security.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_ShouldReturnToken() {
        AuthenticationDTO authDTO = new AuthenticationDTO("user", "pass");
        Authentication authMock = mock(Authentication.class);
        UserModel user = new UserModel("user", "hashed", UserRole.USER);

        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willReturn(authMock);
        given(authMock.getPrincipal()).willReturn(user);
        given(tokenService.generateToken(user)).willReturn("jwt-token");

        ResponseEntity<LoginResponseDTO> response = authController.login(authDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().token());
    }

    @Test
    void register_WhenLoginNotTaken_ShouldCreateUser() {
        RegisterDTO registerDTO = new RegisterDTO("newuser", "pass", UserRole.USER);
        given(userRepository.findByLogin("newuser")).willReturn(null);

        ResponseEntity<Void> response = authController.register(registerDTO);

        assertEquals(200, response.getStatusCode().value());
        then(userRepository).should().save(any(UserModel.class));
    }

    @Test
    void register_WhenLoginTaken_ShouldReturnBadRequest() {
        RegisterDTO registerDTO = new RegisterDTO("existing", "pass", UserRole.USER);
        given(userRepository.findByLogin("existing")).willReturn(mock(org.springframework.security.core.userdetails.UserDetails.class));

        ResponseEntity<Void> response = authController.register(registerDTO);

        assertEquals(400, response.getStatusCode().value());
        then(userRepository).should(org.mockito.Mockito.never()).save(any(UserModel.class));
    }
}
