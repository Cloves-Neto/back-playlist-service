package com.playlist_service.api.infrastructure.web.controller;

import com.playlist_service.api.core.dto.List.ListRequestDTO;
import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.core.usecase.List.ListCreator;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/lists")
public class ListController {

    private final ListCreator listCreator;

    public ListController(ListCreator listCreator){ this.listCreator = listCreator; }

    @PostMapping
    public ResponseEntity<ListResponseDTO> create(@Valid @RequestBody ListRequestDTO request){

        ListResponseDTO response = listCreator.execute(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{listName}")
                .buildAndExpand(response.nome())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

}
