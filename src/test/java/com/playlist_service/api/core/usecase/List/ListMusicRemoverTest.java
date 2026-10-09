package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.dto.Music.MusicRemovalRequestDTO;
import com.playlist_service.api.infrastructure.entity.ListModel;
import com.playlist_service.api.infrastructure.entity.MusicModel;
import com.playlist_service.api.infrastructure.repositories.ListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListMusicRemoverTest {

    @Mock
    private ListRepository listRepository;

    @InjectMocks
    private ListMusicRemover listMusicRemover;

    @Test
    void execute_WhenPlaylistExistsAndMusicsMatch_ShouldRemoveMusics() {
        // Arrange
        String playlistName = "Rock";
        UUID musicId1 = UUID.randomUUID();
        UUID musicId2 = UUID.randomUUID();
        
        MusicModel music1 = MusicModel.builder().id(musicId1).build();
        MusicModel music2 = MusicModel.builder().id(musicId2).build();

        // Usando HashSet mutável para permitir remoção
        ListModel mockList = ListModel.builder()
                .id(UUID.randomUUID())
                .nome(playlistName)
                .musics(new HashSet<>(List.of(music1, music2))) 
                .build();

        MusicRemovalRequestDTO requestDTO = new MusicRemovalRequestDTO(List.of(musicId1));

        when(listRepository.findByNome(playlistName)).thenReturn(Optional.of(mockList));

        // Act
        listMusicRemover.execute(playlistName, requestDTO);

        // Assert
        assertEquals(1, mockList.getMusics().size()); // Removeu 1, sobrou 1
        assertFalse(mockList.getMusics().contains(music1)); // A musica 1 foi removida
        assertTrue(mockList.getMusics().contains(music2)); // A musica 2 continua lá
        
        verify(listRepository, times(1)).save(mockList);
    }

    @Test
    void execute_WhenPlaylistDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        String playlistName = "Pop";
        MusicRemovalRequestDTO requestDTO = new MusicRemovalRequestDTO(List.of(UUID.randomUUID()));

        when(listRepository.findByNome(playlistName)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            listMusicRemover.execute(playlistName, requestDTO);
        });

        assertEquals(404, exception.getStatusCode().value());
        verify(listRepository, never()).save(any());
    }
}
