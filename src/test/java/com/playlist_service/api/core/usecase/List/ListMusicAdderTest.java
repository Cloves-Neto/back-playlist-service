package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.core.dto.Music.MusicAdditionRequestDTO;
import com.playlist_service.api.infrastructure.entity.ListModel;
import com.playlist_service.api.infrastructure.entity.MusicModel;
import com.playlist_service.api.infrastructure.repositories.ListRepository;
import com.playlist_service.api.infrastructure.repositories.MusicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ListMusicAdderTest {

    @Mock
    private ListRepository listRepository;

    @Mock
    private MusicRepository musicRepository;

    @InjectMocks
    private ListMusicAdder listMusicAdder;

    private ListModel listModel;
    private MusicModel musicModel;
    private UUID musicId;

    @BeforeEach
    void setUp() {
        musicId = UUID.randomUUID();
        musicModel = MusicModel.builder()
                .id(musicId)
                .titulo("Bohemian Rhapsody")
                .artista("Queen")
                .album("A Night at the Opera")
                .ano(1975)
                .genero("Rock")
                .build();

        listModel = ListModel.builder()
                .id(UUID.randomUUID())
                .nome("Rock Clássico")
                .descricao("Melhores do rock")
                .musics(new HashSet<>())
                .build();
    }

    @Test
    void execute_WhenListExists_ShouldAddMusicsAndReturnUpdatedList() {
        MusicAdditionRequestDTO request = new MusicAdditionRequestDTO(List.of(musicId));

        given(listRepository.findByNome("Rock Clássico")).willReturn(Optional.of(listModel));
        given(musicRepository.findAllById(request.musicIds())).willReturn(List.of(musicModel));
        given(listRepository.save(any(ListModel.class))).willReturn(listModel);

        ListResponseDTO response = listMusicAdder.execute("Rock Clássico", request);

        assertNotNull(response);
        assertEquals(1, response.musicas().size());
        assertEquals("Bohemian Rhapsody", response.musicas().get(0).titulo());
        then(listRepository).should(times(1)).save(listModel);
    }

    @Test
    void execute_WhenListDoesNotExist_ShouldThrowNotFoundException() {
        MusicAdditionRequestDTO request = new MusicAdditionRequestDTO(List.of(musicId));

        given(listRepository.findByNome("Inexistente")).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> listMusicAdder.execute("Inexistente", request));
        then(listRepository).should(never()).save(any(ListModel.class));
    }
}
