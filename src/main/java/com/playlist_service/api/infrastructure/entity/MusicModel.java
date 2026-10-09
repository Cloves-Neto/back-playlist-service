package com.playlist_service.api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Entity
@Table(name = "musics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MusicModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String artista;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private String album;

    @ManyToMany(mappedBy = "musics")
    @Builder.Default
    private Set<ListModel> lists = new HashSet<>();
}
