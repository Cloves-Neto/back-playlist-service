package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.dto.List.ListResponseDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListFinderByNameTest {

    @Mock
    private ListRepository listRepository;

    @InjectMocks
    private ListFinderByName listFinderByName;

    @Test
    void execute_WhenPlaylistExists_ShouldReturnListResponseDTO() {
        // Arrange
        String playlistName = "Rock Clássico";
        ListModel mockList = ListModel.builder()
                .id(UUID.randomUUID())
                .nome(playlistName)
                .descricao("Melhores rocks")
                .build();

        when(listRepository.findByNome(playlistName)).thenReturn(Optional.of(mockList));

        // Act
        ListResponseDTO result = listFinderByName.execute(playlistName);

        // Assert
        assertNotNull(result);
        assertEquals(playlistName, result.nome());
        verify(listRepository, times(1)).findByNome(playlistName);
    }

    @Test
    void execute_WhenPlaylistDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        String playlistName = "Inexistente";
        when(listRepository.findByNome(playlistName)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            listFinderByName.execute(playlistName);
        });

        assertEquals(404, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Playlist não encontrada!"));
        verify(listRepository, times(1)).findByNome(playlistName);
    }
}
