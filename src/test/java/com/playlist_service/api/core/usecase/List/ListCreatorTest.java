package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.dto.List.ListRequestDTO;
import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.infrastructure.entity.ListModel;
import com.playlist_service.api.infrastructure.repositories.ListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListCreatorTest {

    @Mock
    private ListRepository listRepository;

    @InjectMocks
    private ListCreator listCreator;

    private ListRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ListRequestDTO("Treino Pesado", "Músicas para academia", Set.of());
    }

    @Test
    void execute_WhenNameDoesNotExist_ShouldCreateAndReturnPlaylist() {
        // Arrange
        when(listRepository.findByNome(requestDTO.nome())).thenReturn(Optional.empty());
        
        ListModel savedModel = ListModel.builder()
                .id(UUID.randomUUID())
                .nome("Treino Pesado")
                .descricao("Músicas para academia")
                .build();
                
        when(listRepository.save(any(ListModel.class))).thenReturn(savedModel);

        // Act
        ListResponseDTO response = listCreator.execute(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(savedModel.getId(), response.id());
        assertEquals("Treino Pesado", response.nome());
        verify(listRepository, times(1)).findByNome(requestDTO.nome());
        verify(listRepository, times(1)).save(any(ListModel.class));
    }

    @Test
    void execute_WhenNameAlreadyExists_ShouldThrowResponseStatusException() {
        // Arrange
        ListModel existingModel = ListModel.builder().nome("Treino Pesado").build();
        when(listRepository.findByNome(requestDTO.nome())).thenReturn(Optional.of(existingModel));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            listCreator.execute(requestDTO);
        });
        
        assertEquals(400, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Já existe uma playlist com o nome fornecido!"));
        
        // Garante que não tentou salvar no banco
        verify(listRepository, never()).save(any());
    }
}
