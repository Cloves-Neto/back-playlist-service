package com.playlist_service.api.infrastructure.repositories;

import com.playlist_service.api.infrastructure.entity.ListModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ListRepository extends JpaRepository<ListModel, UUID>{
}
