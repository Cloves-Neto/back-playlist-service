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
public class ListDomain {
    private UUID id;
    private String nome;
    private String descricao;
    
    @Builder.Default
    private Set<MusicDomain> musics = new HashSet<>();
}
