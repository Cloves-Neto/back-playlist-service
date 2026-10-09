package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.core.dto.Music.MusicResponseDTO;
import com.playlist_service.api.infrastructure.entity.MusicModel;
import com.playlist_service.api.infrastructure.repositories.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MusicFinderByNameTest {

    @Mock
    private MusicRepository musicRepository;

    @InjectMocks
    private MusicFinderByName musicFinderByName;

    @Test
    void execute_ShouldReturnMatchingMusics() {
        MusicModel music1 = MusicModel.builder().id(UUID.randomUUID()).titulo("Song A").build();
        given(musicRepository.findByTituloContainingIgnoreCase("Song")).willReturn(List.of(music1));

        List<MusicResponseDTO> result = musicFinderByName.execute("Song");

        assertEquals(1, result.size());
        assertEquals("Song A", result.get(0).titulo());
        then(musicRepository).should().findByTituloContainingIgnoreCase("Song");
    }
}
