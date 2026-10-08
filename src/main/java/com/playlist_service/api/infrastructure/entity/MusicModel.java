package com.playlist_service.api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "musicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @ManyToMany(mappedBy = "musicas")
    @Builder.Default
    private List<ListModel> lists = new ArrayList<>();
}
