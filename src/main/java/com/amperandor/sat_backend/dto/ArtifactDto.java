package com.amperandor.sat_backend.dto;

import com.amperandor.sat_backend.domain.ArtifactType;

import java.time.LocalDateTime;

public record ArtifactDto(
   String filename,
   String directory,
   LocalDateTime createdAt,
   Long size,
   ArtifactType artifactType
) {
}
