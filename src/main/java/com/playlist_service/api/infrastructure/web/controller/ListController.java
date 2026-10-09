package com.playlist_service.api.infrastructure.web.controller;

import com.playlist_service.api.core.dto.List.ListRequestDTO;
import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.core.dto.Music.MusicRemovalRequestDTO;
import com.playlist_service.api.core.usecase.List.ListCreator;
import com.playlist_service.api.core.usecase.List.ListFinder;
import com.playlist_service.api.core.usecase.List.ListDeleteByName;
import com.playlist_service.api.core.usecase.List.ListMusicRemover;
import com.playlist_service.api.core.usecase.List.ListFinderByName;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class ListController {

    private final ListCreator listCreator;
    private final ListFinder listFinder;
    private final ListDeleteByName listDeleteByName;
    private final ListMusicRemover listMusicRemover;
    private final ListFinderByName listFinderByName;

    public ListController(ListCreator listCreator, ListFinder listFinder, ListDeleteByName listDeleteByName, ListMusicRemover listMusicRemover, ListFinderByName listFinderByName){ 
        this.listCreator = listCreator; 
        this.listFinder = listFinder;
        this.listDeleteByName = listDeleteByName;
        this.listMusicRemover = listMusicRemover;
        this.listFinderByName = listFinderByName;
    }

    @PostMapping
    public ResponseEntity<ListResponseDTO> create(@Valid @RequestBody ListRequestDTO request){

        ListResponseDTO response = listCreator.execute(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{listName}")
                .buildAndExpand(response.nome())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ListResponseDTO>> getAllLists(){
        return ResponseEntity.ok(listFinder.execute());
    }

    @GetMapping("/{listName}")
    public ResponseEntity<ListResponseDTO> getListByName(@PathVariable String listName){
        return ResponseEntity.ok(listFinderByName.execute(listName));
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> deleteListByName(@PathVariable String listName){
        listDeleteByName.execute(listName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{listName}/musics")
    public ResponseEntity<Void> removeMusics(
            @PathVariable String listName, 
            @Valid @RequestBody MusicRemovalRequestDTO request){
        
        listMusicRemover.execute(listName, request);
        return ResponseEntity.noContent().build();
    }

}
