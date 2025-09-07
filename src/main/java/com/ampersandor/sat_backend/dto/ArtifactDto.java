package com.ampersandor.sat_backend.dto;

import com.ampersandor.sat_backend.domain.ArtifactType;

import java.time.LocalDateTime;

public record ArtifactDto(
        String id,
        String filename,
        String directory,
        LocalDateTime createdAt,
        Long size,
        ArtifactType artifactType
) {
}
