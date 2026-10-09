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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MusicCreatorTest {

    @Mock
    private MusicRepository musicRepository;

    @InjectMocks
    private MusicCreator musicCreator;

    @Test
    void execute_ShouldCreateAndReturnMusic() {
        MusicRequestDTO requestDTO = new MusicRequestDTO("Title", "Artist", "Genre", 2023, "Album");

        MusicModel savedModel = MusicModel.builder()
                .id(UUID.randomUUID())
                .titulo("Title")
                .artista("Artist")
                .genero("Genre")
                .ano(2023)
                .album("Album")
                .build();

        given(musicRepository.save(any(MusicModel.class))).willReturn(savedModel);

        MusicResponseDTO response = musicCreator.execute(requestDTO);

        assertNotNull(response);
        assertEquals(savedModel.getId(), response.id());
        assertEquals("Title", response.titulo());
        then(musicRepository).should(times(1)).save(any(MusicModel.class));
    }
}
