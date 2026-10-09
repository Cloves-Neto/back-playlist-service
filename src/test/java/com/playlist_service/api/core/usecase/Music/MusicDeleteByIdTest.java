package com.playlist_service.api.core.usecase.Music;

import com.playlist_service.api.infrastructure.repositories.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class MusicDeleteByIdTest {

    @Mock
    private MusicRepository musicRepository;

    @InjectMocks
    private MusicDeleteById musicDeleteById;

    @Test
    void execute_WhenExists_ShouldDelete() {
        UUID id = UUID.randomUUID();
        given(musicRepository.existsById(id)).willReturn(true);

        musicDeleteById.execute(id);

        then(musicRepository).should().deleteById(id);
    }

    @Test
    void execute_WhenDoesNotExist_ShouldThrowException() {
        UUID id = UUID.randomUUID();
        given(musicRepository.existsById(id)).willReturn(false);

        assertThrows(ResponseStatusException.class, () -> musicDeleteById.execute(id));

        then(musicRepository).should(never()).deleteById(id);
    }
}
