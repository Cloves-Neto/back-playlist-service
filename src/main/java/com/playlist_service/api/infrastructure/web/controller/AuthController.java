package com.playlist_service.api.infrastructure.web.controller;

import com.playlist_service.api.core.dto.Auth.AuthenticationDTO;
import com.playlist_service.api.core.dto.Auth.LoginResponseDTO;
import com.playlist_service.api.core.dto.Auth.RegisterDTO;
import com.playlist_service.api.infrastructure.entity.UserModel;
import com.playlist_service.api.infrastructure.repositories.UserRepository;
import com.playlist_service.api.infrastructure.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final com.playlist_service.api.core.usecase.Auth.UserRegistrar userRegistrar;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, com.playlist_service.api.core.usecase.Auth.UserRegistrar userRegistrar) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRegistrar = userRegistrar;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data){
        try {
            userRegistrar.execute(data);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
