package com.playlist_service.api.infrastructure.repositories;

import com.playlist_service.api.infrastructure.entity.MusicModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicRepository extends JpaRepository<MusicModel, UUID>{
}
