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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ListCreatorTest {

    @Mock
    private ListRepository listRepository;

    @Mock
    private com.playlist_service.api.infrastructure.repositories.MusicRepository musicRepository;

    @InjectMocks
    private ListCreator listCreator;

    private ListRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ListRequestDTO("Treino Pesado", "Músicas para academia", java.util.List.of());
    }

    @Test
    void execute_WhenNameDoesNotExist_ShouldCreateAndReturnPlaylist() {
        
        given(listRepository.findByNome(requestDTO.nome())).willReturn(Optional.empty());
        
        ListModel savedModel = ListModel.builder()
                .id(UUID.randomUUID())
                .nome("Treino Pesado")
                .descricao("Músicas para academia")
                .build();
                
        given(listRepository.save(any(ListModel.class))).willReturn(savedModel);

        
        ListResponseDTO response = listCreator.execute(requestDTO);

        assertNotNull(response);
        assertEquals(savedModel.getId(), response.id());
        assertEquals("Treino Pesado", response.nome());
        then(listRepository).should(times(1)).findByNome(requestDTO.nome());
        then(listRepository).should(times(1)).save(any(ListModel.class));
    }

    @Test
    void execute_WhenNameAlreadyExists_ShouldThrowResponseStatusException() {
        
        ListModel existingModel = ListModel.builder().nome("Treino Pesado").build();
        given(listRepository.findByNome(requestDTO.nome())).willReturn(Optional.of(existingModel));

        
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            listCreator.execute(requestDTO);
        });
        
        assertEquals(400, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Já existe uma playlist com o nome fornecido!"));
        
        then(listRepository).should(never()).save(any());
    }
}
