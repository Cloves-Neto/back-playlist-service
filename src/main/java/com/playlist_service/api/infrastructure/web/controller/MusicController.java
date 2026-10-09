package com.playlist_service.api.infrastructure.web.controller;

import com.playlist_service.api.core.dto.Music.MusicRequestDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.core.usecase.Music.MusicCreator;
import com.playlist_service.api.core.usecase.Music.MusicDeleteById;
import com.playlist_service.api.core.usecase.Music.MusicEditor;
import com.playlist_service.api.core.usecase.Music.MusicFinderByName;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/musics")
public class MusicController {

    private final MusicCreator musicCreator;
    private final MusicFinderByName musicFinderByName;
    private final MusicDeleteById musicDeleteById;
    private final MusicEditor musicEditor;

    public MusicController(MusicCreator musicCreator,
                           MusicFinderByName musicFinderByName,
                           MusicDeleteById musicDeleteById,
                           MusicEditor musicEditor) {
        this.musicCreator = musicCreator;
        this.musicFinderByName = musicFinderByName;
        this.musicDeleteById = musicDeleteById;
        this.musicEditor = musicEditor;
    }

    @PostMapping
    public ResponseEntity<MusicResponseDTO> create(@Valid @RequestBody MusicRequestDTO request) {
        MusicResponseDTO response = musicCreator.execute(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MusicResponseDTO>> getByName(@RequestParam String nome) {
        return ResponseEntity.ok(musicFinderByName.execute(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicResponseDTO> edit(@PathVariable UUID id, @Valid @RequestBody MusicRequestDTO request) {
        return ResponseEntity.ok(musicEditor.execute(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        musicDeleteById.execute(id);
        return ResponseEntity.noContent().build();
    }
}
