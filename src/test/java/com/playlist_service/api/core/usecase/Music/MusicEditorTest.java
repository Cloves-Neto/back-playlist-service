package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.core.dto.Music.MusicRequestDTO;
import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.infrastructure.entity.MusicModel;
import com.playlist_service.api.infrastructure.repositories.MusicRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MusicEditorTest {

    @Mock
    private MusicRepository musicRepository;

    @InjectMocks
    private MusicEditor musicEditor;

    @Test
    void execute_WhenExists_ShouldEditAndSave() {
        UUID id = UUID.randomUUID();
        MusicRequestDTO requestDTO = new MusicRequestDTO("New Title", "New Artist", "New Genre", 2024, "New Album");
        
        MusicModel existing = MusicModel.builder()
                .id(id).titulo("Old").artista("Old").genero("Old").ano(2000).album("Old").build();
                
        given(musicRepository.findById(id)).willReturn(Optional.of(existing));
        given(musicRepository.save(any(MusicModel.class))).willAnswer(i -> i.getArguments()[0]);

        MusicResponseDTO result = musicEditor.execute(id, requestDTO);

        assertEquals("New Title", result.titulo());
        then(musicRepository).should().save(existing);
    }

    @Test
    void execute_WhenDoesNotExist_ShouldThrowException() {
        UUID id = UUID.randomUUID();
        MusicRequestDTO requestDTO = new MusicRequestDTO("T", "A", "G", 1, "A");
        given(musicRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> musicEditor.execute(id, requestDTO));
    }
}
