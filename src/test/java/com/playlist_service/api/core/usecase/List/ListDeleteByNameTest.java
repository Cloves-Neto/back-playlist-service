package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.infrastructure.entity.ListModel;
import com.playlist_service.api.infrastructure.repositories.ListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListDeleteByNameTest {

    @Mock
    private ListRepository listRepository;

    @InjectMocks
    private ListDeleteByName listDeleteByName;

    @Test
    void execute_WhenPlaylistExists_ShouldDeletePlaylist() {
        // Arrange
        String playlistName = "Treino Pesado";
        ListModel mockList = ListModel.builder().id(UUID.randomUUID()).nome(playlistName).build();
        
        when(listRepository.findByNome(playlistName)).thenReturn(Optional.of(mockList));

        // Act
        listDeleteByName.execute(playlistName);

        // Assert
        verify(listRepository, times(1)).findByNome(playlistName);
        verify(listRepository, times(1)).delete(mockList);
    }

    @Test
    void execute_WhenPlaylistDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        String playlistName = "Inexistente";
        when(listRepository.findByNome(playlistName)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            listDeleteByName.execute(playlistName);
        });

        assertEquals(404, exception.getStatusCode().value());
        verify(listRepository, times(1)).findByNome(playlistName);
        verify(listRepository, never()).delete(any());
    }
}
