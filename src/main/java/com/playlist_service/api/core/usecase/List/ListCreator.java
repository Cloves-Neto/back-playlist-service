package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.infrastructure.entity.MusicModel;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.infrastructure.entity.ListModel;
import com.playlist_service.api.core.dto.List.ListRequestDTO;
import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.infrastructure.repositories.ListRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.playlist_service.api.infrastructure.repositories.MusicRepository;

@Service
public class ListCreator {

    private final ListRepository listRepository;
    private final MusicRepository musicRepository;

    public ListCreator(ListRepository listRepository, MusicRepository musicRepository) { 
        this.listRepository = listRepository;
        this.musicRepository = musicRepository;
    }

    @Transactional
    public ListResponseDTO execute(ListRequestDTO request) {
        if (listRepository.findByNome(request.nome()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma playlist com o nome fornecido!");
        }
        ListModel list = ListModel.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .build();

        if (request.musicIds() != null && !request.musicIds().isEmpty()) {
            List<MusicModel> existingMusics = musicRepository.findAllById(request.musicIds());
            list.getMusics().addAll(existingMusics);
        }

        if (request.musicas() != null && !request.musicas().isEmpty()) {
            Set<MusicModel> musics = request.musicas().stream()
                    .map(dto -> MusicModel.builder()
                            .titulo(dto.titulo())
                            .artista(dto.artista())
                            .album(dto.album())
                            .ano(dto.ano())
                            .genero(dto.genero())
                            .build())
                    .collect(Collectors.toSet());

            list.getMusics().addAll(musics);
        }

        ListModel savedLists = listRepository.save(list);

        List<MusicResponseDTO> musicResponse = savedLists.getMusics().stream()
                .map(m -> new MusicResponseDTO(
                        m.getId(),
                        m.getTitulo(),
                        m.getArtista(),
                        m.getAlbum(),
                        m.getAno(),
                        m.getGenero()))
                .collect(Collectors.toList());

        return new ListResponseDTO(
                savedLists.getId(),
                savedLists.getNome(),
                savedLists.getDescricao(),
                musicResponse
        );
    }
}
