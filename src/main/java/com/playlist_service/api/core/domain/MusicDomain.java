package com.playlist_service.api.core.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class MusicDomain {
    private UUID id;
    private String titulo;
    private String artista;
    private String genero;
    private Integer ano;
    private String album;
    
    @Builder.Default
    private Set<ListDomain> lists = new HashSet<>();
}
