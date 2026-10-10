package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.domain.ListDomain;
import com.playlist_service.api.core.domain.MusicDomain;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.core.dto.List.ListRequestDTO;
import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.core.ports.out.ListDatabasePort;
import com.playlist_service.api.core.ports.out.MusicDatabasePort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ListCreator {

    private final ListDatabasePort listDatabasePort;
    private final MusicDatabasePort musicDatabasePort;

    public ListCreator(ListDatabasePort listDatabasePort, MusicDatabasePort musicDatabasePort) { 
        this.listDatabasePort = listDatabasePort;
        this.musicDatabasePort = musicDatabasePort;
    }

    @Transactional
    public ListResponseDTO execute(ListRequestDTO request) {
        if (listDatabasePort.findByNome(request.nome()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma playlist com o nome fornecido!");
        }
        ListDomain list = ListDomain.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .build();

        if (request.musicIds() != null && !request.musicIds().isEmpty()) {
            List<MusicDomain> existingMusics = musicDatabasePort.findAllById(request.musicIds());
            list.getMusics().addAll(existingMusics);
        }

        if (request.musicas() != null && !request.musicas().isEmpty()) {
            Set<MusicDomain> musics = request.musicas().stream()
                    .map(dto -> MusicDomain.builder()
                            .titulo(dto.titulo())
                            .artista(dto.artista())
                            .album(dto.album())
                            .ano(dto.ano())
                            .genero(dto.genero())
                            .build())
                    .collect(Collectors.toSet());

            list.getMusics().addAll(musics);
        }

        ListDomain savedLists = listDatabasePort.save(list);

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
