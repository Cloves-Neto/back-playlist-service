package com.playlist_service.api.core.usecase.List;

import com.playlist_service.api.core.dto.List.ListResponseDTO;
import com.playlist_service.api.infrastructure.entity.ListModel;
import com.playlist_service.api.infrastructure.repositories.ListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListFinderTest {

    @Mock
    private ListRepository listRepository;

    @InjectMocks
    private ListFinder listFinder;

    @Test
    void execute_ShouldReturnAllPlaylists() {
        // Arrange
        ListModel list1 = ListModel.builder().id(UUID.randomUUID()).nome("Treino").descricao("...").build();
        ListModel list2 = ListModel.builder().id(UUID.randomUUID()).nome("Relax").descricao("...").build();
        
        when(listRepository.findAll()).thenReturn(List.of(list1, list2));

        // Act
        List<ListResponseDTO> result = listFinder.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Treino", result.get(0).nome());
        assertEquals("Relax", result.get(1).nome());
        verify(listRepository, times(1)).findAll();
    }
}
